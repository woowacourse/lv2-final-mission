package finalmission.presentation;

import finalmission.application.ReservationService;
import finalmission.domain.Schedule;
import finalmission.domain.Ticket;
import finalmission.dto.ReservationRequest;
import finalmission.dto.ReservationResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
        ReservationRequest reservationRequest
    ) {
        return reservationService.saveReservation(reservationRequest);
    }

    @GetMapping
    public List<ReservationResponse> findAllReservation() {
        return reservationService.findAllReservation();
    }

    @GetMapping("/{ticket}")
    public Schedule findReservationByTicket(
        @PathVariable
        Ticket ticket
    ) {
        return reservationService.findSchedule(ticket);
    }

    @DeleteMapping("/{ticket}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(
        @PathVariable
        Ticket ticket
    ) {
        reservationService.deleteReservation(ticket);
    }
}
