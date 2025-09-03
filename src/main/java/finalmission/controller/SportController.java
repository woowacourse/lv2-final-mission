package finalmission.controller;

import finalmission.dto.LoginInfo;
import finalmission.dto.request.SportCreateRequest;
import finalmission.dto.response.SportResponse;
import finalmission.service.SportService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sports")
public class SportController {

    private final SportService sportService;

    public SportController(final SportService sportService) {
        this.sportService = sportService;
    }

    @GetMapping
    public ResponseEntity<List<SportResponse>> getAllReservations() {
        return ResponseEntity.ok(sportService.getAll());
    }

    @PostMapping
    public ResponseEntity<SportResponse> createReservation(@RequestBody final SportCreateRequest request) {
        final SportResponse response = sportService.create(request);
        return ResponseEntity.created(URI.create("reservations/" + response.id())).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeReservation(final LoginInfo loginInfo) {
        sportService.removeSport(loginInfo.id());
        return ResponseEntity.noContent().build();
    }
}
