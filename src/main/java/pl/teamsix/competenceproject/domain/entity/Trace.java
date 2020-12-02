package pl.teamsix.competenceproject.domain.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

//import java.time.LocalDateTime;
import java.sql.Date;

@Document
public class Trace extends BaseEntity {

    @DBRef
    private final User user;

    @DBRef
    private final Hotspot hotspot;

    private final Date entryTime;
    private final Date exitTime;

    public Trace(final User user, final Hotspot hotspot,
                 final Date entryTime, final Date exitTime) {
        this.user = user;
        this.hotspot = hotspot;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    public User getUser() {
        return user;
    }

    public Hotspot getHotspot() {
        return hotspot;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public Date getExitTime() {
        return exitTime;
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
                .append(user, trace.user)
                .append(hotspot, trace.hotspot)
                .append(entryTime, trace.entryTime)
                .append(exitTime, trace.exitTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(user)
                .append(hotspot)
                .append(entryTime)
                .append(exitTime)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("user", user)
                .append("hotspot", hotspot)
                .append("entryTime", entryTime)
                .append("exitTime", exitTime)
                .toString();
    }
}
