package finalmission.reservation.controller;

import finalmission.reservation.dto.CreateReservationRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody CreateReservationRequest reservationRequest) {
        ReservationResponse reservationResponse = reservationService.save(reservationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAll() {
        List<ReservationResponse> reservationResponses = reservationService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(reservationResponses);
    }
}
