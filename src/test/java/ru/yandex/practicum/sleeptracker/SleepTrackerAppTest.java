package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SleepTrackerAppTest {

    static List<SleepingSession> sessions;
    static List<SleepingSession> sessionsEmpty;


    @BeforeAll
    static void create() {
        LocalDateTime timeAsleep1 = LocalDateTime.of(2026, 5, 1, 23, 15);
        LocalDateTime timeWakeUp1 = LocalDateTime.of(2026, 5, 2, 7, 30);
        Quality quality1 = Quality.GOOD;
        SleepingSession sleepingSession1 = new SleepingSession(timeAsleep1, timeWakeUp1, quality1);

        LocalDateTime timeAsleep2 = LocalDateTime.of(2026, 5, 2, 23, 59);
        LocalDateTime timeWakeUp2 = LocalDateTime.of(2026, 5, 3, 5, 0);
        Quality quality2 = Quality.BAD;
        SleepingSession sleepingSession2 = new SleepingSession(timeAsleep2, timeWakeUp2, quality2);

        LocalDateTime timeAsleep3 = LocalDateTime.of(2026, 5, 2, 14, 45);
        LocalDateTime timeWakeUp3 = LocalDateTime.of(2026, 5, 2, 18, 15);
        Quality quality3 = Quality.NORMAL;
        SleepingSession sleepingSession3 = new SleepingSession(timeAsleep3, timeWakeUp3, quality3);

        LocalDateTime timeAsleep4 = LocalDateTime.of(2026, 5, 4, 2, 58);
        LocalDateTime timeWakeUp4 = LocalDateTime.of(2026, 5, 4, 7, 0);
        Quality quality4 = Quality.BAD;
        SleepingSession sleepingSession4 = new SleepingSession(timeAsleep4, timeWakeUp4, quality4);

        sessions = List.of(sleepingSession1, sleepingSession2, sleepingSession3, sleepingSession4);
        sessionsEmpty = new ArrayList<>();
    }

    @Test
    void testTotalSleepSessionsWithCorrectList() {
        TotalSessionsFunction totalSleepSessions = new TotalSessionsFunction();
        SleepAnalysisResult result = totalSleepSessions.analyze(sessions);

        assertEquals(4, result.getValue());
        assertEquals("Общее количество сессий сна за предоставленный период", result.getDescription());
    }

    @Test
    void testTotalSleepSessionsWithEmptyList() {
        TotalSessionsFunction totalSleepSessions = new TotalSessionsFunction();
        List<SleepingSession> sessionsEmpty = new ArrayList<>();
        SleepAnalysisResult result = totalSleepSessions.analyze(sessionsEmpty);

        assertEquals(0, result.getValue());
    }

    @Test
    void testMinDurationWithCorrectList() {
        MinDurationFunction duration = new MinDurationFunction();
        SleepAnalysisResult result = duration.analyze(sessions);

        assertEquals(210L, result.getValue());
        assertEquals("Минимальная продолжительность сна", result.getDescription());
    }

    @Test
    void testMinDurationWithEmptyList() {
        MinDurationFunction duration = new MinDurationFunction();
        SleepAnalysisResult result = duration.analyze(sessionsEmpty);

        assertEquals(0L, result.getValue());
    }

    @Test
    void testMaxDurationWithCorrectList() {
        MaxDurationFunction duration = new MaxDurationFunction();
        SleepAnalysisResult result = duration.analyze(sessions);

        assertEquals(495L, result.getValue());
        assertEquals("Максимальная продолжительность сна", result.getDescription());
    }

    @Test
    void testMaxDurationWithEmptyList() {
        MaxDurationFunction duration = new MaxDurationFunction();
        SleepAnalysisResult result = duration.analyze(sessionsEmpty);

        assertEquals(0L, result.getValue());
    }

    @Test
    void testAverageDurationWithCorrectList() {
        AverageDurationFunction duration = new AverageDurationFunction();
        SleepAnalysisResult result = duration.analyze(sessions);

        assertEquals(312.0, result.getValue());
        assertEquals("Средняя продолжительность сна", result.getDescription());
    }

    @Test
    void testAverageDurationWithEmptyList() {
        AverageDurationFunction duration = new AverageDurationFunction();
        SleepAnalysisResult result = duration.analyze(sessionsEmpty);

        assertEquals(0.0, result.getValue());
    }

    @Test
    void testBadQualityCountFunctionWithCorrectList() {
        BadQualityCountFunction quality = new BadQualityCountFunction();
        SleepAnalysisResult result = quality.analyze(sessions);

        assertEquals(2L, result.getValue());
        assertEquals("Количество сессий с плохим качеством сна", result.getDescription());
    }

    @Test
    void tesBadQualityCountFunctionWithEmptyList() {
        BadQualityCountFunction quality = new BadQualityCountFunction();
        SleepAnalysisResult result = quality.analyze(sessionsEmpty);

        assertEquals(0L, result.getValue());
    }

    @Test
    void testFindSleeplessNightsFunctionWithoutSleeplessNights() {
        FindSleeplessNightsFunction sleeplessNights = new FindSleeplessNightsFunction();
        SleepAnalysisResult result = sleeplessNights.analyze(sessions);

        assertEquals(0, result.getValue());
    }

    @Test
    void testFindSleeplessNightsFunctionWithEmptyList() {
        FindSleeplessNightsFunction sleeplessNights = new FindSleeplessNightsFunction();
        SleepAnalysisResult result = sleeplessNights.analyze(sessionsEmpty);

        assertEquals(0, result.getValue());
    }

    @Test
    void testFindSleeplessNightsFunctionWithTwoSessionButOneSleeplessNights() {
        FindSleeplessNightsFunction sleeplessNights = new FindSleeplessNightsFunction();

        LocalDateTime timeAsleep5 = LocalDateTime.of(2026, 5, 4, 21, 0);
        LocalDateTime timeWakeUp5 = LocalDateTime.of(2026, 5, 4, 23, 59);
        Quality quality5 = Quality.BAD;
        SleepingSession sleepingSession5 = new SleepingSession(timeAsleep5, timeWakeUp5, quality5);

        LocalDateTime timeAsleep6 = LocalDateTime.of(2026, 5, 5, 6, 1);
        LocalDateTime timeWakeUp6 = LocalDateTime.of(2026, 5, 5, 12, 0);
        Quality quality6 = Quality.NORMAL;
        SleepingSession sleepingSession6 = new SleepingSession(timeAsleep6, timeWakeUp6, quality6);

        List<SleepingSession> sessionsWithSleepless = new ArrayList<>(sessions);
        sessionsWithSleepless.add(sleepingSession5);
        sessionsWithSleepless.add(sleepingSession6);

        SleepAnalysisResult result = sleeplessNights.analyze(sessionsWithSleepless);

        assertEquals(1, result.getValue());
        assertEquals("Количество бессонных ночей", result.getDescription());
    }

    @Test
    void testFindSleeplessNightsFunctionWithTwoSessionAndTwoSleeplessNights() {
        FindSleeplessNightsFunction sleeplessNights = new FindSleeplessNightsFunction();

        LocalDateTime timeAsleep5 = LocalDateTime.of(2026, 5, 4, 21, 0);
        LocalDateTime timeWakeUp5 = LocalDateTime.of(2026, 5, 4, 23, 59);
        Quality quality5 = Quality.BAD;
        SleepingSession sleepingSession5 = new SleepingSession(timeAsleep5, timeWakeUp5, quality5);

        LocalDateTime timeAsleep6 = LocalDateTime.of(2026, 5, 6, 6, 1);
        LocalDateTime timeWakeUp6 = LocalDateTime.of(2026, 5, 6, 12, 0);
        Quality quality6 = Quality.NORMAL;
        SleepingSession sleepingSession6 = new SleepingSession(timeAsleep6, timeWakeUp6, quality6);

        List<SleepingSession> sessionsWithSleepless = new ArrayList<>(sessions);
        sessionsWithSleepless.add(sleepingSession5);
        sessionsWithSleepless.add(sleepingSession6);

        SleepAnalysisResult result = sleeplessNights.analyze(sessionsWithSleepless);

        assertEquals(2, result.getValue());
        assertEquals("Количество бессонных ночей", result.getDescription());
    }

    @Test
    void testFindSleeplessNightsFunctionWithTransitionInMonth() {
        FindSleeplessNightsFunction sleeplessNights = new FindSleeplessNightsFunction();

        LocalDateTime timeAsleep5 = LocalDateTime.of(2026, 4, 30, 20, 0);
        LocalDateTime timeWakeUp5 = LocalDateTime.of(2026, 4, 30, 23, 59);
        Quality quality5 = Quality.BAD;
        SleepingSession sleepingSession5 = new SleepingSession(timeAsleep5, timeWakeUp5, quality5);

        List<SleepingSession> sessionsWithSleepless = new ArrayList<>(sessions);
        sessionsWithSleepless.addFirst(sleepingSession5);

        SleepAnalysisResult result = sleeplessNights.analyze(sessionsWithSleepless);

        assertEquals(1, result.getValue());
        assertEquals("Количество бессонных ночей", result.getDescription());
    }

    @Test
    void testClassificationUsersFunctionWithCorrectList() {
        ClassificationUsersFunction classification = new ClassificationUsersFunction();
        SleepAnalysisResult result = classification.analyze(sessionsEmpty);

        assertEquals("Голубь", result.getValue());
        assertEquals("Хронотип пользователя", result.getDescription());

    }

    @Test
    void testClassificationUsersFunctionWithEmptyList() {
        ClassificationUsersFunction classification = new ClassificationUsersFunction();
        SleepAnalysisResult result = classification.analyze(sessionsEmpty);

        assertEquals("Голубь", result.getValue());
    }

    @Test
    void testClassificationUsersFunctionWithOneOwlAndLark() {
        ClassificationUsersFunction classification = new ClassificationUsersFunction();

        LocalDateTime timeAsleep1 = LocalDateTime.of(2026, 5, 10, 23, 0);
        LocalDateTime timeWakeUp1 = LocalDateTime.of(2026, 5, 11, 10, 10);
        Quality quality1 = Quality.GOOD;
        SleepingSession sleepingSession1 = new SleepingSession(timeAsleep1, timeWakeUp1, quality1);

        LocalDateTime timeAsleep2 = LocalDateTime.of(2026, 5, 11, 21, 15);
        LocalDateTime timeWakeUp2 = LocalDateTime.of(2026, 5, 12, 6, 45);
        Quality quality2 = Quality.NORMAL;
        SleepingSession sleepingSession2 = new SleepingSession(timeAsleep2, timeWakeUp2, quality2);

        List<SleepingSession> sessionsTwo = new ArrayList<>();
        sessionsTwo.add(sleepingSession1);
        sessionsTwo.add(sleepingSession2);

        SleepAnalysisResult result = classification.analyze(sessionsTwo);

        assertEquals("Голубь", result.getValue());
    }
}