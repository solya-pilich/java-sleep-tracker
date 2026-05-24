package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class MaxDurationFunction implements SleepAnalyzer {

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        Long duration = sessions.stream()
                .mapToLong(session -> Duration.between(session.getTimeAsleep(),
                        session.getTimeWakeUp()).toMinutes())
                .max()
                .orElse(0L);
        return new SleepAnalysisResult(duration, "Максимальная продолжительность сна");
    }
}
