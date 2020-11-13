package pl.teamsix.competenceproject.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Trace extends BaseEntity {

    @DBRef
    private User user;
    @DBRef
    private Hotspot hotspot;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public User getUser() {
        return user;
    }

    public Hotspot getHotspot() {
        return hotspot;
    }
}
