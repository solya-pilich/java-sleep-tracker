package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class MinDurationFunction implements SleepAnalyzer {

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        Long duration = sessions.stream()
                .mapToLong(session -> Duration.between(session.getTimeAsleep(),
                        session.getTimeWakeUp()).toMinutes())
                .min()
                .orElse(0L);
        return new SleepAnalysisResult(duration, "Минимальная продолжительность сна");
    }
}
