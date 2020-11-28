package pl.teamsix.competenceproject.domain.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Hotspot extends BaseEntity {
    private String name;
    private String type;
    private double x;
    private double y;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Hotspot() {
        super();
    }

    public Hotspot(String name, String type, double x, double y) {
        super();
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Hotspot{" + "id" + getId() + "name='" + name + '\'' + ", type='" + type + '\'' + ", x=" + x + ", y=" + y
                + '}';
    }

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
