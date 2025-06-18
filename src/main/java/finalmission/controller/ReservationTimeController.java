package finalmission.controller;

import finalmission.annotation.CheckRole;
import finalmission.domain.MemberRole;
import finalmission.dto.request.CreateReservationTimeRequest;
import finalmission.dto.response.CreateReservationTimeResponse;
import finalmission.dto.response.ReservationTimeResponse;
import finalmission.service.ReservationTimeService;
import java.util.List;
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
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public List<ReservationTimeResponse> getReservationTimes() {
        return reservationTimeService.findAllReservationTime();
    }

    @CheckRole({MemberRole.ADMIN})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateReservationTimeResponse addReservationTime(@RequestBody CreateReservationTimeRequest request) {
        return reservationTimeService.addReservationTime(request);
    }

    @CheckRole({MemberRole.ADMIN})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.deleteReservationTime(id);
    }
}
