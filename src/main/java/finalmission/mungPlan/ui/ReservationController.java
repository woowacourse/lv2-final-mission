package finalmission.mungPlan.ui;

import finalmission.mungPlan.application.ReservationService;
import finalmission.mungPlan.ui.dto.CreateReservationRequest;
import finalmission.mungPlan.ui.dto.ReservationResponse;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReservationResponse createReservation(@RequestBody CreateReservationRequest request) {
        return reservationService.createReservation(request);
    }

    @GetMapping("/{reservationId}")
    public ReservationResponse getReservation(@PathVariable Long reservationId) {
        return reservationService.getById(reservationId);
    }

    @GetMapping("/mine/{userId}")
    public List<ReservationResponse> getAllReservationsByUserId(@PathVariable String userId) {
        return reservationService.getAllByUserId(userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}/{reservationId}")
    public void deleteReservation(@PathVariable String userId,
                                  @PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId, userId);
    }
}
