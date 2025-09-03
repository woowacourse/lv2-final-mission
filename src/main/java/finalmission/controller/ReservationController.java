package finalmission.controller;

import finalmission.dto.LoginInfo;
import finalmission.dto.request.ReservationCreateRequest;
import finalmission.dto.response.ReservationResponse;
import finalmission.service.ReservationService;
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
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAll());
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody final ReservationCreateRequest request) {
        final ReservationResponse response = reservationService.create(request);
        return ResponseEntity.created(URI.create("reservations/" + response.id())).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeReservation(final LoginInfo loginInfo) {
        reservationService.removeReservation(loginInfo.id());
        return ResponseEntity.noContent().build();
    }
}
