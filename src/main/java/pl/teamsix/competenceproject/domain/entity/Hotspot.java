package pl.teamsix.competenceproject.domain.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Hotspot extends BaseEntity {
    private String name;
    private String description;
    private String type;
    private float x;
    private float y;
}
