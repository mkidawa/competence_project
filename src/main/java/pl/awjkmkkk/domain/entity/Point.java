package pl.awjkmkkk.domain.entity;

public class Point extends BaseEntity {

    public enum PointType {

        INDOOR("indoor"),
        OUTDOOR("outdoor");

        private final String name;

        PointType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    private String name;
    private String description;
    private PointType type;
    private float position;

    public Point(String name, String description, PointType type, float position) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PointType getType() {
        return type;
    }

    public void setType(PointType type) {
        this.type = type;
    }

    public float getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position = position;
    }
}
