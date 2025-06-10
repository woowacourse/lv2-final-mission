package finalmission.presentation;

import finalmission.dto.request.ReservationRequest;
import finalmission.dto.response.ReservationResponse;
import finalmission.presentation.auth.Authenticated;
import finalmission.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(@Authenticated Long memberId,
                                                       @RequestBody ReservationRequest request) {
        ReservationResponse response = reservationService.createReservation(memberId, request);
        return ResponseEntity.ok(response);
    }
}
