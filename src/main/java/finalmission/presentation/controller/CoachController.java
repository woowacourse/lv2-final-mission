package finalmission.presentation.controller;

import finalmission.application.dto.CoachResponse;
import finalmission.application.dto.CrewResponse;
import finalmission.application.service.CoachService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coachs")
public class CoachController {
    private final CoachService coachService;

    public CoachController(final CoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping
    public ResponseEntity<List<CoachResponse>> getCoaches(
    ){
        return ResponseEntity.ok().body(
                coachService.getCoaches()
        );
    }


}
