package sm.poe.builds.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sm.poe.builds.entity.Build;
import sm.poe.builds.entity.PoeClass;
import sm.poe.builds.model.BuildDto;
import sm.poe.builds.model.GemDto;
import sm.poe.builds.model.PoeClassDto;
import sm.poe.builds.repository.BuildRepository;
import sm.poe.builds.repository.PoeClassRepository;
import sm.poe.builds.service.BuildService;
import sm.poe.builds.service.GemService;
import sm.poe.builds.utility.PoeBuildFilter;
import sm.poe.builds.utility.PoeBuildMapper;
import sm.poe.builds.utility.PoeClassMapper;

import java.util.List;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@RequiredArgsConstructor
@Service
public class BuildServiceImpl implements BuildService {

    private final BuildRepository buildRepository;
    private final PoeClassRepository poeClassRepository;
    private final GemService gemService;
    private final PoeBuildMapper poeBuildMapper;
    private final PoeClassMapper poeClassMapper;

    @Override
    public List<BuildDto> findBuilds(PoeBuildFilter poeBuildFilter) {
        String version = poeBuildFilter.getVersion();
        String poeClass = poeBuildFilter.getPoeClass();

        List<BuildDto> buildDtos;
        if (isNotBlank(version) && isNotBlank(poeClass)) {
            buildDtos = poeBuildMapper.entityToModel(buildRepository.findByPoeClassNameAndVersion(poeClass, version));
        } else if (isNotBlank(version)) {
            buildDtos = poeBuildMapper.entityToModel(buildRepository.findByVersion(version));
        } else if (isNotBlank(poeClass)) {
            buildDtos = poeBuildMapper.entityToModel(buildRepository.findByPoeClassName(poeClass));
        } else {
            buildDtos = poeBuildMapper.entityToModel(buildRepository.findAll());
        }

        List<GemDto> gems = gemService.findGems();

        buildDtos.stream().forEach(buildDto -> {
            String buildName = buildDto.getName();
            gems.stream().forEach(gem -> {
                String gemName = gem.getName();
                int startIndex = buildName.indexOf(gemName);
                if (startIndex >= 0) {
                    String wrapBuildName = "<img src=\"" + gem.getImageUrl() + "\">" +
                            "<a style='color: " + gem.getColor() + "'>" + gemName + "</a>";
                    buildDto.setName(buildName.replace(gemName, wrapBuildName));
                }
            });
        });

        return buildDtos;
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
