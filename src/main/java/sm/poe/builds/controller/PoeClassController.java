package sm.poe.builds.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.poe.builds.model.PoeClassDto;
import sm.poe.builds.service.PoeClassService;

import java.util.List;

@RestController
@RequestMapping(path = "/classes")
@RequiredArgsConstructor
public class PoeClassController {

    private final PoeClassService poeClassService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<PoeClassDto>> getPoeClasses() {
        return ResponseEntity.ok(poeClassService.getPoeClasses());
    }
}
