package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FindSleeplessNightsFunction implements SleepAnalyzer {

    static final int MIDDAY = 12;

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {

        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(0, "Количество бессонных ночей");
        }

        LocalDate dateStart = findFirstNight(sessions);
        LocalDate dateFinish = sessions.getLast().getTimeWakeUp().toLocalDate();
        Period period = Period.between(dateStart, dateFinish);
        int totalNight = period.getDays() + 1;

        Set<LocalDate> countNightSleep;
        countNightSleep = sessions.stream()
                .filter(this::isNightSleep)
                .map(session -> session.getTimeAsleep().toLocalDate())
                .collect(Collectors.toSet());

        sessions.stream()
                .filter(session -> session.getTimeWakeUp().getHour() < 6)
                .map(session -> session.getTimeWakeUp().toLocalDate())
                .forEach(countNightSleep::add);

        int countSleepless = totalNight - countNightSleep.size();
        return new SleepAnalysisResult(countSleepless, "Количество бессонных ночей");
    }

    public LocalDate findFirstNight(List<SleepingSession> sessions) {
        LocalDateTime firstSession = sessions.getFirst().getTimeAsleep();
        int timeFirstSession = firstSession.toLocalTime().getHour();
        if (timeFirstSession < MIDDAY) {
            return firstSession.toLocalDate().minusDays(1);
        } else {
            return firstSession.toLocalDate();
        }
    }

    public boolean isNightSleep(SleepingSession session) {
        LocalDate date = session.getTimeWakeUp().toLocalDate();
        LocalDateTime startNight = date.atStartOfDay();
        LocalDateTime finishNight = startNight.plusHours(6);
        return session.getTimeAsleep().isBefore(finishNight) && session.getTimeWakeUp().isAfter(startNight);
    }
}
