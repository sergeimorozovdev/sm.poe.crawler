package sm.poe.builds.utility;

import org.springframework.stereotype.Component;
import sm.poe.builds.entity.Gem;
import sm.poe.builds.model.GemDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GemMapper {
    public Gem modelToEntity(GemDto gemDto) {
        return Gem.builder()
                .name(gemDto.getName())
                .imageUrl(gemDto.getImageUrl())
                .type(gemDto.getType())
                .color(gemDto.getColor())
                .build();
    }

    public GemDto entityToModel(Gem gem) {
        return GemDto.builder()
                .name(gem.getName())
                .imageUrl(gem.getImageUrl())
                .color(gem.getColor())
                .type(gem.getType())
                .build();
    }

    public List<GemDto> entityToModel(List<Gem> gems) {
        return gems.stream().map(this::entityToModel).toList();
    }

    public Map<String, GemDto> entityToMapModel(List<Gem> gems) {
        return gems.stream().collect(Collectors.toMap(Gem::getName, this::entityToModel));
    }
}
