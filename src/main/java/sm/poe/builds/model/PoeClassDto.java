package sm.poe.builds.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PoeClassDto
{
	private final String name;
	private List<BuildDto> builds;
	
}
