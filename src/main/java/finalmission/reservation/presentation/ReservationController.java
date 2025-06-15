package finalmission.reservation.presentation;

import finalmission.auth.presentation.Authenticated;
import finalmission.reservation.dto.request.ReservationRequest;
import finalmission.reservation.dto.response.ReservationResponse;
import finalmission.reservation.service.ReservationService;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public List<ReservationResponse> getReservations(
            @RequestParam(value = "toiletId", required = false) Long toiletId,
            @RequestParam(value = "date", required = false) LocalDate date
    ) {
        return reservationService.findReservations(toiletId, date);
    }

    @GetMapping("/me")
    public List<ReservationResponse> getMyReservations(@Authenticated Long memberId) {
        return reservationService.findReservationsByMemberId(memberId);
    }
}
