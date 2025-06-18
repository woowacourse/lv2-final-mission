package finalmission.reservationTime.controller;

import finalmission.reservationTime.controller.dto.request.ReservationTimeRequest;
import finalmission.reservationTime.controller.dto.response.ReservationTimeResponse;
import finalmission.reservationTime.domain.ReservationTime;
import finalmission.reservationTime.service.ReservationTimeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/time")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> create(@RequestBody @Valid ReservationTimeRequest request) {
        ReservationTime reservationTime =reservationTimeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ReservationTimeResponse.from(reservationTime));
    }
}
