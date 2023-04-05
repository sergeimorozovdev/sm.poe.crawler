package sm.poe.builds.service;

import org.springframework.stereotype.Service;
import sm.poe.builds.model.BuildDto;
import sm.poe.builds.model.PoeClassDto;
import sm.poe.builds.utility.Filter;

import java.util.List;

@Service
public interface BuildService {

    List<BuildDto> findBuilds(Filter filter);

    void saveBuilds(List<PoeClassDto> poeClassDtos);
}
