package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class BadQualityCountFunction implements SleepAnalyzer {

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        Long count = sessions.stream()
                .filter(session -> session.getQuality().equals(Quality.BAD))
                .count();
        return new SleepAnalysisResult(count, "Количество сессий с плохим качеством сна");
    }
}
