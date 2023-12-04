package sm.poe.builds.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class PoeClassDto
{
	private final String name;
	private List<BuildDto> builds;
	
}
