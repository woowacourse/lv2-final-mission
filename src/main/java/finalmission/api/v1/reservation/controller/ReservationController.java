package finalmission.api.v1.reservation.controller;

import finalmission.api.v1.reservation.dto.ReservationRequest;
import finalmission.api.v1.reservation.dto.ReservationResponse;
import finalmission.api.v1.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse resisterReservation(@Valid @RequestBody final ReservationRequest request) {
        return reservationService.resisterReservation(request);
    }
}
