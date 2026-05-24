package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class TotalSessionsFunction implements SleepAnalyzer {

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        int numberOfSessions = sessions.size();
        return new SleepAnalysisResult(numberOfSessions, "Общее количество сессий сна за предоставленный период");
    }
}
