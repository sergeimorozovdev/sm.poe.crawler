package sm.poe.builds.service;

import org.springframework.stereotype.Service;
import sm.poe.builds.model.BuildDto;
import sm.poe.builds.model.PoeClassDto;
import sm.poe.builds.utility.PoeBuildFilter;

import java.util.List;

@Service
public interface BuildService {

    List<BuildDto> findBuilds(PoeBuildFilter poeBuildFilter);

    void saveBuilds(List<PoeClassDto> poeClassDtos);
}
