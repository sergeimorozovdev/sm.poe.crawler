package sm.poe.builds.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import sm.poe.builds.entity.Build;
import sm.poe.builds.entity.PoeClass;
import sm.poe.builds.model.BuildDto;
import sm.poe.builds.model.GemDto;
import sm.poe.builds.repository.BuildRepository;
import sm.poe.builds.repository.PoeClassRepository;
import sm.poe.builds.service.BuildService;
import sm.poe.builds.service.GemService;
import sm.poe.builds.utility.PoeBuildFilter;
import sm.poe.builds.utility.PoeBuildMapper;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@RequiredArgsConstructor
@Service
public class BuildServiceImpl implements BuildService {

    private final BuildRepository buildRepository;
    private final PoeClassRepository poeClassRepository;
    private final GemService gemService;
    private final PoeBuildMapper poeBuildMapper;

    @Override
    public List<BuildDto> findBuilds(PoeBuildFilter poeBuildFilter) {
        String version = poeBuildFilter.getVersion();
        String poeClassName = poeBuildFilter.getPoeClassName();
        String search = poeBuildFilter.getSearch();

        List<BuildDto> buildDtos;
        if (isNotBlank(version) && isNotBlank(poeClassName) && !poeClassName.equals(PoeClassServiceImpl.ALL_CLASSES)) {
            buildDtos = poeBuildMapper.entityToModel(buildRepository.findByPoeClassNameAndVersion(poeClassName, version));
        } else if (isNotBlank(version)) {
            buildDtos = poeBuildMapper.entityToModel(buildRepository.findByVersion(version));
        } else if (isNotBlank(poeClassName)) {
            buildDtos = poeBuildMapper.entityToModel(buildRepository.findByPoeClassName(poeClassName));
        } else {
            buildDtos = poeBuildMapper.entityToModel(buildRepository.findAll());
        }

        if (Strings.isNotBlank(search)) {
            buildDtos = buildDtos.stream().filter(
                            buildDto -> buildDto.getName().toLowerCase()
                                    .contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        setTextColor(buildDtos);

        return buildDtos;
    }


    @Override
    public void saveBuilds(List<BuildDto> poeBuilds) {
        buildRepository.deleteAll();
        List<PoeClass> poeClasses = poeClassRepository.findAll();
        List<Build> builds = poeBuilds.stream().map(buildDto ->
        {
            PoeClass poeClass = poeClasses.stream()
                    .filter(pc -> pc.getName().equals(buildDto.getPoeClassName()))
                    .findFirst()
                    .get();
            return poeBuildMapper.modelToEntity(buildDto, poeClass);
        }).collect(Collectors.toList());
        buildRepository.saveAllAndFlush(builds);
    }

    private void setTextColor(List<BuildDto> buildDtos) {
        List<GemDto> gems = gemService.findGems();
        buildDtos.forEach(buildDto -> {
            String buildName = buildDto.getName().toLowerCase();
            gems.forEach(gem -> {
                String gemName = gem.getName().toLowerCase();
                int startIndex = buildName.indexOf(gemName);
                if (startIndex >= 0) {
                    String wrapBuildName = /*"<img src=\"" + gem.getImageUrl() + "\">" +*/
                            "<a style='color: " + gem.getColor() + "'>" + gemName + "</a>";
                    buildDto.setName(buildName.replace(gemName, wrapBuildName));
                }
            });
        });
    }
}
