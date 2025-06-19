package finalmission.presentation.controller;

import finalmission.application.ReservationService;
import finalmission.presentation.AuthenticationElement;
import finalmission.presentation.dto.ReservationRequest;
import finalmission.presentation.dto.ReservationResponse;
import finalmission.presentation.dto.YogaSessionForBookingResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public List<YogaSessionForBookingResponse> getYogaSessionsForBooking(@RequestParam LocalDate date) {
        var sessions = reservationService.getYogaSessionsForBooking(date);
        return YogaSessionForBookingResponse.from(sessions);
    }

    @PostMapping()
    public ReservationResponse reserve(@RequestBody ReservationRequest request, @AuthenticationElement Long memberId) {
        var reservation = reservationService.register(memberId, request.sessionId());
        return ReservationResponse.from(reservation);
    }

    @GetMapping("/mine")
    public List<ReservationResponse> getMyReservations(@AuthenticationElement Long memberId) {
        var reservations = reservationService.getMyReservations(memberId);
        return ReservationResponse.from(reservations);
    }

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable Long id, @AuthenticationElement Long memberId) {
        reservationService.cancel(id, memberId);
    }
}
