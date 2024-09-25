package io.github.gusthavo.srt;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SRTParser {

	public final List<Subtitle> subtitles = new ArrayList<>();

	final static Pattern PATTERN_NUMBERS = Pattern.compile("(\\d+)");
	final static Pattern PATTERN_TIME = Pattern.compile("([\\d]{2}:[\\d]{2}:[\\d]{2},[\\d]{3}).*([\\d]{2}:[\\d]{2}:[\\d]{2},[\\d]{3})");

	/**
	 * Verilen string içerisindeki altyazıları alır ve altyazı listesine dönüştürür.
	 *
	 * @param subtitleString Altyazı
	 * @return               Altyazı listesi
	 */
	private List<Subtitle> getSubtitlesFromString(String subtitleString) {

		List<Subtitle> subtitles = new ArrayList<>();
		Subtitle subtitle;
		StringBuilder srt = new StringBuilder();

		String[] lines = subtitleString.split("\n");
		for (int i = 0; i < lines.length; i++) {
			subtitle = new Subtitle();

			String line = lines[i];

			/* Boş satırları atla */
			if (line.isEmpty())
				continue;

			Matcher matcher = PATTERN_NUMBERS.matcher(line);
			if (matcher.find()) {
				subtitle.setId(Integer.parseInt(matcher.group(1)));
				line = lines[++i];
			}

			matcher = PATTERN_TIME.matcher(line);
			if (matcher.find()) {
				subtitle.setStartTime(matcher.group(1));
				subtitle.setEndTime(matcher.group(2));
			}

			srt.setLength(0);

			while (++i < lines.length && !(line = lines[i]).isEmpty())
				srt.append(line).append("\n");

			subtitle.setText(srt.toString().trim());
			subtitles.add(subtitle);
		}

		return subtitles;
	}

	/**
	 * Verilen altyazıyı mevcut altyazı listesine ekler.
	 *
	 * @param subtitleToMerge   Eklenecek altyazı
	 */
	public void mergeSubtitles(@NotNull String subtitleToMerge) {

		List<Subtitle> mergedSubtitleList = getSubtitlesFromString(subtitleToMerge.trim());

		if (this.subtitles.isEmpty()) {
			this.subtitles.addAll(mergedSubtitleList);
			return;
		}

		/*Listenin mevcut boyutunu al ve bir değişkende sakla */
		int currentSize = this.subtitles.size();

		/* Son altyazının bitiş zamanı bilgisini al */
		long lastTimeOut = convertSubtitleTimeToMillis(this.subtitles.get(currentSize - 1).getEndTime());

		for (Subtitle subtitle : mergedSubtitleList) {
			subtitle.setId(++currentSize);

			/* Zamanları milisaniye cinsine dönüştür ve önceki altyazı süresini ekle */
			long startTime = convertSubtitleTimeToMillis(subtitle.getStartTime()) + lastTimeOut;
			long endTime = convertSubtitleTimeToMillis(subtitle.getEndTime()) + lastTimeOut;

			subtitle.setStartTime(convertMillisToSubtitleFormat(startTime));
			subtitle.setEndTime(convertMillisToSubtitleFormat(endTime));

			this.subtitles.add(subtitle);
		}
	}

	/**
	 * Long cinsinden verilen milisaniye bilgisii stringe dönüştürür.
	 *
	 * @param time Milisaniye bilgisi
	 * @return     String cinsinden zaman bilgisi
	 */
	private String convertMillisToSubtitleFormat(final long time) {

		Duration duration = Duration.ofMillis(time);

		long hours = duration.toHours();
		int minutes = duration.toMinutesPart();
		int seconds = duration.toSecondsPart();
		int milliseconds = duration.toMillisPart();

		return String.format("%02d:%02d:%02d,%03d", hours, minutes, seconds, milliseconds);
	}

	/**
	 * String cinsinden verilen zaman bilgisini milisaniye cinsine dönüştürür.
	 *
	 * @param time Zaman bilgisi
	 * @return     Milisaniye cinsinden zaman bilgisi
	 */
	private long convertSubtitleTimeToMillis(final String time) {

		if (time == null)
			throw new RuntimeException("Time must not be null");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss,SSS");
		LocalTime localTime = LocalTime.parse(time, formatter);

		return localTime.toNanoOfDay() / 1000000;
	}

	@Data
	@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
	public static class Subtitle {

		Integer id;
		String startTime;
		String endTime;
		String text;

		@Override
		public String toString() {
			return id + "\n" + startTime + " --> " + endTime + "\n" + text + "\n\n";
		}
	}

	@Override
	public String toString() {
		return this.subtitles.stream().map(SRTParser.Subtitle::toString).collect(Collectors.joining()).trim();
	}
}