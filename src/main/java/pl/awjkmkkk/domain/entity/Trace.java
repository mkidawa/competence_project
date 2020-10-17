package pl.awjkmkkk.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Trace extends BaseEntity {

    /*------------------------ FIELDS REGION ------------------------*/

    private UUID userId;
    private UUID pointId;
    private LocalDateTime timeOfEntry;
    private LocalDateTime timeOfExit;

    /*------------------------ METHODS REGION ------------------------*/

    public Trace(UUID userId, UUID pointId, LocalDateTime timeOfEntry, LocalDateTime timeOfExit) {
        this.userId = userId;
        this.pointId = pointId;
        this.timeOfEntry = timeOfEntry;
        this.timeOfExit = timeOfExit;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getPointId() {
        return pointId;
    }

    public void setPointId(UUID pointId) {
        this.pointId = pointId;
    }

    public LocalDateTime getTimeOfEntry() {
        return timeOfEntry;
    }

    public void setTimeOfEntry(LocalDateTime timeOfEntry) {
        this.timeOfEntry = timeOfEntry;
    }

    public LocalDateTime getTimeOfExit() {
        return timeOfExit;
    }

    public void setTimeOfExit(LocalDateTime timeOfExit) {
        this.timeOfExit = timeOfExit;
    }

    public void getTimeSpent() {
        // TODO Implement method
    }
}
