package learnb.yoga.models;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum UserType {

    STUDENT("Student"),
    INSTRUCTOR("Instructor");

private final String label;


UserType(String label) {this.label = label;}

    public String getLabel()

    {return label;}

    public static List<Map<String, String>> toList() {
        return Arrays.stream(UserType.values())
                .map(a -> Map.of("value", a.name(), "label", a.getLabel()))
                .sorted((a, b) -> a.get("label").compareToIgnoreCase(b.get("label")))
                .collect(Collectors.toList());
    }


}
