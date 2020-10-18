package pl.awjkmkkk.domain.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Trace trace = (Trace) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(userId, trace.userId)
                .append(pointId, trace.pointId)
                .append(timeOfEntry, trace.timeOfEntry)
                .append(timeOfExit, trace.timeOfExit)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(userId)
                .append(pointId)
                .append(timeOfEntry)
                .append(timeOfExit)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userId", userId)
                .append("pointId", pointId)
                .append("timeOfEntry", timeOfEntry)
                .append("timeOfExit", timeOfExit)
                .toString();
    }
}
