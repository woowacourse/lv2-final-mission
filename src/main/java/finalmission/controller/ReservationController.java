package finalmission.controller;

import finalmission.dto.CreateReservationRequest;
import finalmission.dto.CreateReservationResponse;
import finalmission.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/schedules/{id}/reservations")
    public ResponseEntity<CreateReservationResponse> createReservation(
            @PathVariable("id") final long scheduleId,
            @RequestBody CreateReservationRequest request
    ) {
        final CreateReservationResponse response = reservationService.createReservation(scheduleId, request);
        return ResponseEntity.ok().body(response);
    }
}
