package sm.poe.builds.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PoeBuildFilter {
    private String version;
    private String poeClass;
    private String search;
}
