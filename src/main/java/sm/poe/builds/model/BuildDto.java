package sm.poe.builds.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BuildDto
{
	private String name;
	private String url;
	private String version;
	private Integer views;
	private String author;
	private String poeClass;
	
	@Override
	public String toString()
	{
		return "Build{" +
				"name='" + name + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}
