package finalmission.music.controller;

import finalmission.music.controller.dto.ReservationRequest;
import finalmission.music.controller.dto.ReservationResponse;
import finalmission.music.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> reserve(@RequestBody final ReservationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.reserve(request));
    }
}
