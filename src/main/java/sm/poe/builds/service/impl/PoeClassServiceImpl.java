package sm.poe.builds.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sm.poe.builds.model.PoeClassDto;
import sm.poe.builds.repository.PoeClassRepository;
import sm.poe.builds.service.PoeClassService;
import sm.poe.builds.utility.PoeClassMapper;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PoeClassServiceImpl implements PoeClassService {

    private final PoeClassRepository poeClassRepository;
    private final PoeClassMapper poeClassMapper;

    @Override
    public List<PoeClassDto> getPoeClasses() {
        return poeClassMapper.entityToModel(poeClassRepository.findAll());
    }

    @Override
    public void savePoeClasses(List<PoeClassDto> dtos) {
        poeClassRepository.saveAllAndFlush(poeClassMapper.modelToEntity(dtos));
    }
}
