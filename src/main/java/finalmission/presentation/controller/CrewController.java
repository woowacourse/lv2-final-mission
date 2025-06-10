package finalmission.presentation.controller;

import finalmission.application.dto.CrewResponse;
import finalmission.application.service.CrewService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crews")
public class CrewController {

    private final CrewService crewService;

    public CrewController(final CrewService crewService) {
        this.crewService = crewService;
    }

    @GetMapping
    public ResponseEntity<List<CrewResponse>> getCrews(
    ){
        return ResponseEntity.ok().body(
                crewService.getCrews()
        );
    }

}
