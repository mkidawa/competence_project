package pl.awjkmkkk.domain.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Point point = (Point) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(position, point.position)
                .append(name, point.name)
                .append(description, point.description)
                .append(type, point.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(name)
                .append(description)
                .append(type)
                .append(position)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("name", name)
                .append("description", description)
                .append("type", type)
                .append("position", position)
                .toString();
    }
}
