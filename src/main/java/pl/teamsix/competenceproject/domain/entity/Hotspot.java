package pl.teamsix.competenceproject.domain.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Hotspot extends BaseEntity {
    private String name;
    private String description;
    private String type;
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
