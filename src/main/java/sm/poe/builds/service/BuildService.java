package sm.poe.builds.service;

import sm.poe.builds.model.BuildDto;
import sm.poe.builds.model.PoeClassDto;
import sm.poe.builds.utility.Filter;

import java.util.List;

public interface BuildService {

    List<BuildDto> findBuilds(Filter filter);

    void saveBuilds(List<PoeClassDto> poeClassDtos);
}
