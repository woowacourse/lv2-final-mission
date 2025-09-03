package finalmission.controller;

import finalmission.dto.LoginInfo;
import finalmission.dto.request.ReservationTimeCreateRequest;
import finalmission.dto.response.ReservationTimeResponse;
import finalmission.service.ReservationTimeService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getAllReservationTimes() {
        return ResponseEntity.ok(reservationTimeService.getAll());
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createReservationTime(
            @RequestBody final ReservationTimeCreateRequest request,
            @RequestBody final ReservationTimeCreateRequest requ2
    ) {
        final ReservationTimeResponse response = reservationTimeService.create(request);
        return ResponseEntity.created(URI.create("times/" + response.id())).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeReservationTime(final LoginInfo loginInfo) {
        reservationTimeService.removeReservationTime(loginInfo.id());
        return ResponseEntity.noContent().build();
    }
}
