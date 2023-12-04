package sm.poe.builds.service;

import org.springframework.stereotype.Service;
import sm.poe.builds.model.GemDto;

import java.util.List;
import java.util.Map;

@Service
public interface GemService {

    List<GemDto> findGems();

    Map<String, GemDto> findGemsAsMap();

    void saveGems(List<GemDto> gemDtos);
}
