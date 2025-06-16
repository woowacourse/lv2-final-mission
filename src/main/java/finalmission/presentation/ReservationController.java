package finalmission.presentation;

import finalmission.application.ReservationService;
import finalmission.domain.Ticket;
import finalmission.presentation.dto.ReservationRequest;
import finalmission.presentation.dto.ReservationResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket saveReservation(
        @Valid
        @RequestBody
        final ReservationRequest reservationRequest
    ) {
        return reservationService.saveReservation(reservationRequest);
    }

    @GetMapping
    public List<ReservationResponse> findAllReservation() {
        return reservationService.findAllReservation();
    }

    @GetMapping("/{ticket}")
    public ReservationResponse findSchedule(
        @PathVariable
        final Ticket ticket
    ) {
        return reservationService.findReservation(ticket);
    }

    @PatchMapping("/{ticket}/cancel")
    public ReservationResponse cancelReservation(
        @PathVariable
        final Ticket ticket
    ) {
        return reservationService.cancelReservation(ticket);
    }
}
