package pl.teamsix.competenceproject.domain.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Hotspot extends BaseEntity {

    private String name;
    private String type;
    private double x;
    private double y;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Hotspot hotspot = (Hotspot) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(x, hotspot.x)
                .append(y, hotspot.y)
                .append(name, hotspot.name)
                .append(type, hotspot.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(name)
                .append(type)
                .append(x)
                .append(y)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("name", name)
                .append("type", type)
                .append("x", x)
                .append("y", y)
                .toString();
    }
}
