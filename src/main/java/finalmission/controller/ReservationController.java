package finalmission.controller;

import finalmission.controller.dto.ReservationRequest;
import finalmission.controller.dto.ReservationResponse;
import finalmission.controller.dto.ReservationUpdateRequest;
import finalmission.domain.reservation.entity.Reservation;
import finalmission.domain.reservation.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody final ReservationRequest request) {
        final Reservation reservation = reservationService.create(
                request.name(),
                request.phoneNumber(),
                request.lesson(),
                request.date(),
                request.time()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(ReservationResponse.from(reservation));
    }

    @GetMapping("/current-situations")
    public ResponseEntity<List<ReservationResponse>> getCurrentSituations() {
        final List<Reservation> reservations = reservationService.getCurrentSituations();
        final List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/mine/{id}")
    public ResponseEntity<List<ReservationResponse>> getMyReservations(@PathVariable final Long id) {
        final List<Reservation> reservations = reservationService.getMyReservations(id);
        final List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservations(@PathVariable final Long id) {
        reservationService.deleteReservation(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ReservationResponse> updateReservations(
            @PathVariable final Long id,
            @RequestBody final ReservationUpdateRequest request) {
        final Reservation reservation = reservationService.findReservation(id);

        reservationService.updateReservation(reservation, request.date(), request.time());

        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }
}
