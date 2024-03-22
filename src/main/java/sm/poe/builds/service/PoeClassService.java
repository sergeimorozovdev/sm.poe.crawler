package sm.poe.builds.service;

import org.springframework.stereotype.Service;
import sm.poe.builds.model.PoeClassDto;

import java.util.List;

@Service
public interface PoeClassService {

    List<PoeClassDto> getPoeClasses();

    void savePoeClasses(List<PoeClassDto> dtos);
}
