package sm.poe.builds.service;

import org.springframework.stereotype.Service;
import sm.poe.builds.model.BuildDto;
import sm.poe.builds.model.GemDto;
import sm.poe.builds.utility.Filter;

import java.util.List;

@Service
public interface GemService {

    List<BuildDto> findGems(Filter filter);

    void saveGems(List<GemDto> gemDtos);
}
