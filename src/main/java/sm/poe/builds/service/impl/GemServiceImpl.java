package sm.poe.builds.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sm.poe.builds.entity.Gem;
import sm.poe.builds.model.GemDto;
import sm.poe.builds.repository.GemRepository;
import sm.poe.builds.service.GemService;
import sm.poe.builds.utility.GemMapper;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GemServiceImpl implements GemService {

    private final GemRepository gemRepository;
    private final GemMapper gemMapper;

    @Override
    public List<GemDto> findGems() {
        return gemMapper.entityToModel(gemRepository.findAll());
    }

    @Override
    public Map<String, GemDto> findGemsAsMap() {
        return gemMapper.entityToMapModel(gemRepository.findAll());
    }

    @Override
    public void saveGems(List<GemDto> gemDtos) {
        gemRepository.deleteAll();
        gemDtos.forEach(dto ->
        {
            Gem gem = gemMapper.modelToEntity(dto);
            gemRepository.saveAndFlush(gem);
        });
        gemRepository.flush();
    }
}
