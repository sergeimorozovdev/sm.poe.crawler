package sm.poe.builds.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class GemDto {
    private String name;
    private String type;
    private String color;
    private String imageUrl;
}
