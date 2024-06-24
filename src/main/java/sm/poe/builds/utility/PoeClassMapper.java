package sm.poe.builds.utility;

import org.springframework.stereotype.Component;
import sm.poe.builds.entity.PoeClass;
import sm.poe.builds.model.PoeClassDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PoeClassMapper {
    public PoeClass modelToEntity(PoeClassDto poeClassDto) {
        return PoeClass.builder()
                .name(poeClassDto.getName())
                .build();
    }

    public List<PoeClass> modelToEntity(List<PoeClassDto> poeClassDto) {
        return poeClassDto.stream().map(this::modelToEntity).toList();
    }

    public PoeClassDto entityToModel(PoeClass poeClass) {
        return PoeClassDto.builder()
                .name(poeClass.getName())
                .build();
    }

    public List<PoeClassDto> entityToModel(List<PoeClass> poeClasses) {
        return poeClasses.stream().map(this::entityToModel).collect(Collectors.toList());
    }
}
