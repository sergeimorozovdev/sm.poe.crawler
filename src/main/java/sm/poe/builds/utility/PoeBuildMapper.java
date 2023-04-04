package sm.poe.builds.utility;

import org.springframework.stereotype.Component;
import sm.poe.builds.entity.Build;
import sm.poe.builds.entity.PoeClass;
import sm.poe.builds.model.BuildDto;

import java.util.List;

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
	
	public BuildDto entityToModel(Build build)
	{
		return BuildDto.builder()
				.name(build.getName())
				.url(build.getUrl())
				.version(build.getVersion())
				.views(build.getViews())
				.author(build.getAuthor())
				.poeClass(build.getPoeClass().getName())
				.build();
	}

	public List<BuildDto> entityToModel(List<Build> builds){
		return builds.stream().map(this::entityToModel).toList();
	}
}
