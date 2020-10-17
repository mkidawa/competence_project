package pl.awjkmkkk.logic.model.type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PointType {

    /*------------------------ FIELDS REGION ------------------------*/
    INDOOR("indoor"),
    OUTDOOR("outdoor");

    private final String name;

    /*------------------------ METHODS REGION ------------------------*/
    PointType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PointType fromString(final String text) {
        return Arrays.asList(PointType.values())
                .stream()
                .filter((it) -> it.getName().equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<String> getNamesList() {
        return Arrays.asList(PointType.values())
                .stream()
                .map((it) -> it.getName())
                .collect(Collectors.toList());
    }
}
