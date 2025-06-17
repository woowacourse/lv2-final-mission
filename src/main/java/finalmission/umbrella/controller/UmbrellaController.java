package finalmission.umbrella.controller;

import finalmission.umbrella.dto.response.UmbrellaResponse;
import finalmission.umbrella.service.UmbrellaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UmbrellaController {

    private final UmbrellaService umbrellaService;

    @PostMapping("/umbrellas")
    public ResponseEntity<UmbrellaResponse> registerUmbrella(){
        UmbrellaResponse umbrella = umbrellaService.createUmbrella();
        return ResponseEntity.ok(umbrella);
    }
}
