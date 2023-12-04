package sm.poe.builds.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class BuildDto
{
	private String name;
	private String url;
	private String version;
	private Integer views;
	private String author;
	private String poeClass;
}
