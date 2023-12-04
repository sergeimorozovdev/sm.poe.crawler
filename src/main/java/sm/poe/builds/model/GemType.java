package sm.poe.builds.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GemType {
    AWAKENED_GEM("AwakenedGem"),
    SKILL_GEM("SkillGemsGem"),
    SUPPORT_GEM("SupportGemsGem");

    private final String id;
}
