package pl.teamsix.competenceproject.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Trace extends BaseEntity {

    @DBRef
    private final User user;

    @DBRef
    private final Hotspot hotspot;

    private final LocalDateTime entryTime;
    private final LocalDateTime exitTime;

    public Trace(final User user, final Hotspot hotspot, final LocalDateTime entryTime, final LocalDateTime exitTime) {
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

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }
}
