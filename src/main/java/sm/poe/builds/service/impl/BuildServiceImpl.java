package sm.poe.builds.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sm.poe.builds.entity.Build;
import sm.poe.builds.entity.PoeClass;
import sm.poe.builds.model.BuildDto;
import sm.poe.builds.model.PoeClassDto;
import sm.poe.builds.repository.BuildRepository;
import sm.poe.builds.repository.PoeClassRepository;
import sm.poe.builds.service.BuildService;
import sm.poe.builds.utility.Filter;
import sm.poe.builds.utility.PoeBuildMapper;
import sm.poe.builds.utility.PoeClassMapper;

import java.util.List;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@RequiredArgsConstructor
@Service
public class BuildServiceImpl implements BuildService {

    private final BuildRepository buildRepository;
    private final PoeClassRepository poeClassRepository;
    private final PoeBuildMapper poeBuildMapper;
    private final PoeClassMapper poeClassMapper;

    @Override
    public List<BuildDto> findBuilds(Filter filter) {
        String version = filter.getVersion();
        String poeClass = filter.getPoeClass();

        if (isNotBlank(version) && isNotBlank(poeClass)) {
            return poeBuildMapper.entityToModel(buildRepository.findByPoeClassNameAndVersion(poeClass, version));
        } else if (isNotBlank(version)) {
            return poeBuildMapper.entityToModel(buildRepository.findByVersion(version));
        } else if (isNotBlank(poeClass)) {
            return poeBuildMapper.entityToModel(buildRepository.findByPoeClassName(poeClass));
        }
        return poeBuildMapper.entityToModel(buildRepository.findAll());
    }

    @Override
    public void saveBuilds(List<PoeClassDto> poeClassDtos) {
        buildRepository.deleteAll();
        poeClassDtos.forEach(dto ->
        {
            PoeClass poeClass = poeClassMapper.modelToEntity(dto);
            poeClassRepository.saveAndFlush(poeClass);
            List<Build> builds = dto.getBuilds()
                    .stream()
                    .map(b -> poeBuildMapper.modelToEntity(b, poeClass))
                    .toList();
            buildRepository.saveAll(builds);
        });
        buildRepository.flush();
    }
}
