package sm.poe.builds.utility;

import org.springframework.stereotype.Component;
import sm.poe.builds.entity.PoeClass;
import sm.poe.builds.model.PoeClassDto;

@Component
public class PoeClassMapper
{
	public PoeClass modelToEntity(PoeClassDto poeClassDto)
	{
		return PoeClass.builder()
				.name(poeClassDto.getClassName())
				.build();
	}
	
	public PoeClassDto entityToModel(PoeClass poeClass)
	{
		return PoeClassDto.builder()
				.className(poeClass.getName())
				.build();
	}
}
