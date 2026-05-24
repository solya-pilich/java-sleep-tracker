package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ClassificationUsersFunction implements SleepAnalyzer {

    static final String OWL = "Сова";
    static final String LARK = "Жаворонок";
    static final String PIGEON = "Голубь";

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(PIGEON, "Хронотип пользователя");
        }

        List<String> birdList = sessions.stream()
                .filter(this::isNightSleep)
                .map(this::ClassifyNight)
                .toList();

        long countOwl = birdList.stream().filter(OWL::equals).count();
        long countLark = birdList.stream().filter(LARK::equals).count();
        long countPigeon = birdList.stream().filter(PIGEON::equals).count();

        String result;
        if (countOwl > countLark && countOwl > countPigeon) {
            result = OWL;
        } else if (countLark > countOwl && countLark > countPigeon) {
            result = LARK;
        } else {
            result = PIGEON;
        }
        return new SleepAnalysisResult(result, "Хронотип пользователя");
    }

    public String ClassifyNight(SleepingSession session) {
        LocalTime timeAsleep = session.getTimeAsleep().toLocalTime();
        LocalTime timeWakeUp = session.getTimeWakeUp().toLocalTime();

        if (timeAsleep.isAfter(LocalTime.of(23, 0))
                && timeWakeUp.isAfter(LocalTime.of(9, 0))) {
            return OWL;
        } else if (timeAsleep.isBefore(LocalTime.of(22, 0))
                && timeWakeUp.isBefore(LocalTime.of(7, 0))) {
            return LARK;
        } else {
            return PIGEON;
        }
    }

    public boolean isNightSleep(SleepingSession session) {
        LocalDate date = session.getTimeWakeUp().toLocalDate();
        LocalDateTime startNight = date.atStartOfDay();
        LocalDateTime finishNight = startNight.plusHours(6);
        return session.getTimeAsleep().isBefore(finishNight) && session.getTimeWakeUp().isAfter(startNight);
    }
}
