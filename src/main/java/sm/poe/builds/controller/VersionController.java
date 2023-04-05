package sm.poe.builds.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.poe.builds.service.VersionService;

import java.util.Set;

@RestController
@RequestMapping(path = "/versions")
@RequiredArgsConstructor
public class VersionController {

    private final VersionService versionService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<Set<String>> getVersions() {
        return ResponseEntity.ok(versionService.getVersions());
    }
}
