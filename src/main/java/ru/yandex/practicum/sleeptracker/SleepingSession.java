package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepingSession {

    private LocalDateTime timeAsleep;
    private LocalDateTime timeWakeUp;
    private Quality quality;

    public SleepingSession(LocalDateTime timeAsleep, LocalDateTime timeWakeUp, Quality quality) {
        this.timeAsleep = timeAsleep;
        this.timeWakeUp = timeWakeUp;
        this.quality = quality;
    }

    public Quality getQuality() {
        return quality;
    }

    public LocalDateTime getTimeAsleep() {
        return timeAsleep;
    }

    public LocalDateTime getTimeWakeUp() {
        return timeWakeUp;
    }
}
