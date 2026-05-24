package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {

    Object value;
    String description;

    public SleepAnalysisResult(Object value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Object getValue() {
        return value;
    }
}
