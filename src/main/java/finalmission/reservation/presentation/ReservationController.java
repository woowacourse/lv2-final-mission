package finalmission.reservation.presentation;

import finalmission.auth.presentation.Authenticated;
import finalmission.reservation.dto.request.ReservationRequest;
import finalmission.reservation.dto.response.ReservationResponse;
import finalmission.reservation.service.ReservationService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(
            @Authenticated Long memberId,
            @RequestBody @Valid ReservationRequest request
    ) {
        ReservationResponse response = reservationService.createReservation(memberId, request);
        return ResponseEntity.created(URI.create("/reservations/" + response.id())).body(response);
    }
}
