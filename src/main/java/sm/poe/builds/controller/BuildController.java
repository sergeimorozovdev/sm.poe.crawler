package sm.poe.builds.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sm.poe.builds.model.BuildDto;
import sm.poe.builds.service.BuildService;
import sm.poe.builds.utility.PoeBuildFilter;

import java.util.List;

@RestController
@RequestMapping(path = "/builds")
@RequiredArgsConstructor
public class BuildController {

    private final BuildService buildService;

    @GetMapping(produces = "application/json")
    ResponseEntity<List<BuildDto>> getBuilds(
            @RequestParam(required = false) String poeClassName,
            @RequestParam(required = false) String version,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(buildService.findBuilds(new PoeBuildFilter(version, poeClassName, search)));
    }
}