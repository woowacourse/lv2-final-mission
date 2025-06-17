package finalmission.reservation.controller;

import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.request.ReservationsFindAllRequest;
import finalmission.reservation.dto.request.ReservationRequest;
import finalmission.reservation.dto.response.ReservationResponse;
import finalmission.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationService.createReservation(reservationRequest);
        ReservationResponse reservationResponse = ReservationResponse.from(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(reservationResponse);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getAllReservations(@RequestBody ReservationsFindAllRequest request) {
        List<Reservation> allReservations = reservationService.findAllReservations(request.localDate());
        List<ReservationResponse> reservationResponses = allReservations.stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @GetMapping("/reservations-mine")
    public ResponseEntity<List<ReservationResponse>> getAllReservationsMine(@RequestBody  ReservationRequest request) {
        List<Reservation> reservations = reservationService.findReservations(request);
        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @DeleteMapping("/reservations")
    public ResponseEntity<Void> deleteReservation(@RequestBody ReservationRequest reservationRequest) {
        reservationService.deleteReservation(reservationRequest);
        return ResponseEntity.noContent().build();
    }
}
