package pl.teamsix.competenceproject.domain.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class Trace extends BaseEntity {

    /*------------------------ FIELDS REGION ------------------------*/
    @DBRef
    private Person person;
    @DBRef
    private Point point;
    private LocalDateTime timeOfEntry;
    private LocalDateTime timeOfExit;

    /*------------------------ METHODS REGION ------------------------*/
    public Trace(Person person, Point point, LocalDateTime timeOfEntry, LocalDateTime timeOfExit) {
        this.person = person;
        this.point = point;
        this.timeOfEntry = timeOfEntry;
        this.timeOfExit = timeOfExit;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
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
                .append(person, trace.person)
                .append(point, trace.point)
                .append(timeOfEntry, trace.timeOfEntry)
                .append(timeOfExit, trace.timeOfExit)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(person)
                .append(point)
                .append(timeOfEntry)
                .append(timeOfExit)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("person", person)
                .append("point", point)
                .append("timeOfEntry", timeOfEntry)
                .append("timeOfExit", timeOfExit)
                .toString();
    }
}
