package sm.poe.builds.utility;

import org.springframework.stereotype.Component;
import sm.poe.builds.entity.Build;
import sm.poe.builds.entity.PoeClass;
import sm.poe.builds.model.BuildDto;

@Component
public class PoeBuildMapper
{
	public Build modelToEntity(BuildDto buildDto, PoeClass poeClass)
	{
		return Build.builder()
				.name(buildDto.getName())
				.url(buildDto.getUrl())
				.version(buildDto.getVersion())
				.views(buildDto.getViews())
				.author(buildDto.getAuthor())
				.poeClass(poeClass)
				.build();
	}
	
	public BuildDto entityToModel(Build parsedBuild)
	{
		return BuildDto.builder()
				.name(parsedBuild.getName())
				.url(parsedBuild.getUrl())
				.version(parsedBuild.getVersion())
				.views(parsedBuild.getViews())
				.author(parsedBuild.getAuthor())
				.build();
	}
}
