package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    private static final List<SleepAnalyzer> functions = new ArrayList<>();

    static {
        functions.add(new TotalSessionsFunction());
        functions.add(new MinDurationFunction());
        functions.add(new MaxDurationFunction());
        functions.add(new AverageDurationFunction());
        functions.add(new BadQualityCountFunction());
        functions.add(new FindSleeplessNightsFunction());
        functions.add(new ClassificationUsersFunction());
    }

    public static void main(String[] args) {

        String filePath;
        if (args.length == 0) {
            filePath = "src/main/resources/sleep_log.txt";
        } else {
            filePath = args[0];
        }

        Path path = Paths.get(filePath);
        List<SleepingSession> sessions;
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            sessions = br.lines()
                    .map(SleepTrackerApp::createSession)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        functions.stream()
                .map(function -> function.analyze(sessions))
                .forEach(result -> System.out.println(result.getDescription() + ": "
                        + result.getValue()));
    }

    private static SleepingSession createSession(String line) {
        String[] linePart = line.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        LocalDateTime timeAsleep = LocalDateTime.parse(linePart[0], formatter);
        LocalDateTime timeWakeUp = LocalDateTime.parse((linePart[1]), formatter);
        Quality quality = Quality.valueOf(linePart[2]);
        return new SleepingSession(timeAsleep, timeWakeUp, quality);
    }
}