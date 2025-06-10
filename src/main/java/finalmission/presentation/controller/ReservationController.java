package finalmission.presentation.controller;

import finalmission.application.ReservationService;
import finalmission.application.dto.ReservationRequest;
import finalmission.application.dto.ReservationResponse;
import finalmission.domain.Reservation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping()
    public Reservation reserve(@RequestBody ReservationRequest request, Long userId) {
        return reservationService.register(userId, request.classId(), request.date(), request.time());
    }

    @GetMapping("/mine")
    public List<ReservationResponse> getMyReservations(Long userId) {
        var reservations = reservationService.getMyReservations(userId);
        return ReservationResponse.from(reservations);
    }

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable Long id, Long userId) {
        reservationService.cancel(id, userId);
    }
}
