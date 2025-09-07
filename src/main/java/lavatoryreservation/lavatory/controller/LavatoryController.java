package lavatoryreservation.lavatory.controller;

import lavatoryreservation.lavatory.domain.Lavatory;
import lavatoryreservation.lavatory.service.LavatoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lavatory/")
public class LavatoryController {

    private final LavatoryService lavatoryService;

    public LavatoryController(LavatoryService lavatoryService) {
        this.lavatoryService = lavatoryService;
    }

    @PostMapping
    public ResponseEntity<Long> addLavatory(@RequestBody Lavatory lavatory) {
        Lavatory createdLavatory = lavatoryService.addLavatory(lavatory);
        return ResponseEntity.ok(createdLavatory.getId());
    }
}
