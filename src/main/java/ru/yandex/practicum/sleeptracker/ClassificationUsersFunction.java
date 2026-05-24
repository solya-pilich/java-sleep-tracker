package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ClassificationUsersFunction implements SleepAnalyzer {

    static final String OWL = "Сова";
    static final String LARK = "Жаворонок";
    static final String PIGEON = "Голубь";

    static final LocalTime OWL_ASLEEP_AFTER = LocalTime.of(23, 0);
    static final LocalTime OWL_WAKE_AFTER = LocalTime.of(9, 0);
    static final LocalTime LARK_ASLEEP_BEFORE = LocalTime.of(22, 0);
    static final LocalTime LARK_WAKE_BEFORE = LocalTime.of(7, 0);

    static final int NIGHT_DURATION_HOURS = 6;

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(PIGEON, "Хронотип пользователя");
        }

        List<String> birdList = sessions.stream()
                .filter(this::isNightSleep)
                .map(this::classifyNight)
                .toList();

        long countOwl = findCount(birdList, OWL);
        long countLark = findCount(birdList, LARK);
        long countPigeon = findCount(birdList, PIGEON);

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

    public String classifyNight(SleepingSession session) {
        LocalTime timeAsleep = session.getTimeAsleep().toLocalTime();
        LocalTime timeWakeUp = session.getTimeWakeUp().toLocalTime();

        if (timeAsleep.isAfter(OWL_ASLEEP_AFTER)
                && timeWakeUp.isAfter(OWL_WAKE_AFTER)) {
            return OWL;
        } else if (timeAsleep.isBefore(LARK_ASLEEP_BEFORE)
                && timeWakeUp.isBefore(LARK_WAKE_BEFORE)) {
            return LARK;
        } else {
            return PIGEON;
        }
    }

    public boolean isNightSleep(SleepingSession session) {
        LocalDate date = session.getTimeWakeUp().toLocalDate();
        LocalDateTime startNight = date.atStartOfDay();
        LocalDateTime finishNight = startNight.plusHours(NIGHT_DURATION_HOURS);
        return session.getTimeAsleep().isBefore(finishNight) && session.getTimeWakeUp().isAfter(startNight);
    }

    public long findCount(List<String> birdList, String bird) {
        return birdList.stream().filter(bird::equals).count();
    }
}
