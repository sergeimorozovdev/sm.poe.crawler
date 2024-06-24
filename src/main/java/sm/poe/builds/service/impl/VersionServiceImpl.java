package sm.poe.builds.service.impl;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sm.poe.builds.service.VersionService;

import java.util.Set;

@Service
@Setter
public class VersionServiceImpl implements VersionService {

    @Value("${poe.versions}")
    private Set<String> versions;

    @Override
    public Set<String> getVersions() {
        return versions;
    }
}
