package finalmission.omakase.controller;

import finalmission.omakase.controller.dto.OmakaseCreateRequest;
import finalmission.omakase.entity.Omakase;
import finalmission.omakase.service.OmakaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/omakases")
public class OmakaseController {

    private final OmakaseService omakaseService;

    public OmakaseController(OmakaseService omakaseService) {
        this.omakaseService = omakaseService;
    }

    @PostMapping
    public ResponseEntity<Omakase> save(
            @Valid @RequestBody OmakaseCreateRequest omakaseCreateRequest
    ) {
        Omakase save = omakaseService.save(omakaseCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }
}
