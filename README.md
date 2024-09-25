# SRTParser

SRTParser, SRT dosyaları ve altyazı dosyaları ile çalışmak için ihtiyacınız olan her şeyi sağlar.

## Kurulum

Android cihazlar için optimize edilmiş hafif bir sürüm olduğundan, kodu klonlayıp projenize eklemek en iyi kullanım yoludur.

## Katkıda Bulunma

Pull request'ler memnuniyetle karşılanır.

Lütfen uygun şekilde testleri güncellemeyi unutmayın.

## Bu projenin amacı nedir?

Bu projenin amacı, Java veya Android geliştiricilerine kolay, hızlı, performanslı ve hafif bir srt kütüphanesi sağlamaktır.

String formattaki SRT değerlerinizi kolayca birleştirmenize olanak tanır.

## Projenin kullanımı:

```java
package io.github.gusthavo.srt;

public class DemoApp {
    public static void main(String[] args) {

        SRTParser parser = new SRTParser();

        String srt1 = "1\n" +
                "00:00:00,000 --> 00:00:02,500\n" +
                "Sessiz lüks, gösterişten uzak ama derin bir zenginliği ifade eder.\n\n" +
                "2\n" +
                "00:00:02,500 --> 00:00:06,000\n" +
                "Markaların logolarından çok, kalite ve sadeliğe önem verir.\n\n" +
                "3\n" +
                "00:00:06,000 --> 00:00:09,500\n" +
                "İnsanlar artık daha zarif, göz önünde olmayan zenginliği tercih ediyor.\n\n" +
                "4\n" +
                "00:00:09,500 --> 00:00:13,000\n" +
                "Bu, öne çıkma çabası değil, kendini tanıyan bir tavır.\n\n" +
                "5\n" +
                "00:00:13,000 --> 00:00:15,000\n" +
                "Sessiz lüks, bir yaşam tarzıdır.\n";

        parser.mergeSubtitles(srt1);

        String srt2 = "1\n" +
                "00:00:00,000 --> 00:00:03,000\n" +
                "Minimalizm, modern yaşamın karmaşasından bir kaçış yoludur.\n\n" +
                "2\n" +
                "00:00:03,000 --> 00:00:07,000\n" +
                "Az ama öz yaşam, fazlalıklardan arınarak huzuru bulmayı amaçlar.\n\n" +
                "3\n" +
                "00:00:07,000 --> 00:00:10,000\n" +
                "Eşyaların çokluğu yerine, anlam ve işlev ön plandadır.\n\n" +
                "4\n" +
                "00:00:10,000 --> 00:00:13,500\n" +
                "Bu, tüketim toplumuna bir tepki ve bilinçli bir tercih.\n\n" +
                "5\n" +
                "00:00:13,500 --> 00:00:15,000\n" +
                "Minimalizm, sade bir zenginliktir.\n";

        parser.mergeSubtitles(srt2);
        System.out.println(parser);
    }
}
```

Sürüm 0.1.0