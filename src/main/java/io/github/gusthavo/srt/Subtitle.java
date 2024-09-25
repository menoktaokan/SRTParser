package io.github.gusthavo.srt;

public class Subtitle {

    Integer id;
    String startTime;
    String endTime;
    String text;

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return id + "\n" + startTime + " --> " + endTime + "\n" + text + "\n\n";
    }
}