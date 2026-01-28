package finalmission.stall.controller;

import finalmission.stall.controller.dto.request.StallCreateRequest;
import finalmission.stall.controller.dto.response.StallCreateResponse;
import finalmission.stall.controller.dto.response.StallInfosResponse;
import finalmission.stall.service.StallService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/stalls")
public class StallController {

    private final StallService stallService;

    public StallController(StallService stallService) {
        this.stallService = stallService;
    }

    @PostMapping
    public ResponseEntity<StallCreateResponse> postStall(@RequestBody StallCreateRequest request) {
        StallCreateResponse response = stallService.create(request);
        return ResponseEntity.created(URI.create("/stalls/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<StallInfosResponse> getStalls() {
        StallInfosResponse response = stallService.findStalls();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStall(@PathVariable Long id) {
        stallService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
