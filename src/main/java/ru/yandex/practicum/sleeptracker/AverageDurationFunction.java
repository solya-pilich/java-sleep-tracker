package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class AverageDurationFunction implements SleepAnalyzer {

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        Double duration = sessions.stream()
                .mapToLong(session -> Duration.between(session.getTimeAsleep(),
                        session.getTimeWakeUp()).toMinutes())
                .average()
                .orElse(0.0);
        return new SleepAnalysisResult(duration, "Средняя продолжительность сна");
    }
}
