package sm.poe.builds.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface VersionService {
    Set<String> getVersions();
}
