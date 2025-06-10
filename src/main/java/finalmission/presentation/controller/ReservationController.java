package finalmission.presentation.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import finalmission.application.ReservationService;
import finalmission.domain.reservation.Reservation;
import finalmission.presentation.request.CreateReservationRequest;
import finalmission.presentation.response.ReservationResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    @ResponseStatus(CREATED)
    public ReservationResponse createReservation(
            @RequestBody @Valid final CreateReservationRequest request
    ) {
        Reservation reservation = reservationService.createReservation(
                request.customerId(),
                request.date(),
                request.time(),
                request.designId(),
                request.designerId()
        );
        return ReservationResponse.fromReservation(reservation);
    }

    @GetMapping("reservations")
    public List<ReservationResponse> readReservations(final Long customerId) {
        List<Reservation> reservations = reservationService.getReservationsByCustomerId(customerId);
        return ReservationResponse.fromReservations(reservations);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteReservation(@PathVariable("id") final long reservationId) {
        reservationService.removeReservation(reservationId);
    }
}
