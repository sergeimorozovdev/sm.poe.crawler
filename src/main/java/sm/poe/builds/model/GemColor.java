package sm.poe.builds.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GemColor {
    RED("red"),
    GREEN("green"),
    BLUE("blue"),
    OTHER("other");

    private final String id;
}
