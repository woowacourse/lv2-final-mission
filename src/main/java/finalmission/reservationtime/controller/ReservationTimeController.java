package finalmission.reservationtime.controller;

import finalmission.reservationtime.dto.ReservationTimeAvailableRequest;
import finalmission.reservationtime.dto.ReservationTimeAvailableResponse;
import finalmission.reservationtime.dto.ReservationTimeRequest;
import finalmission.reservationtime.dto.ReservationTimeResponse;
import finalmission.reservationtime.service.ReservationTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationTimeController {

    private final ReservationTimeService timeService;

    @PostMapping("/admin/reservationTimes")
    public ResponseEntity<ReservationTimeResponse> create(
            @RequestBody ReservationTimeRequest request
    ) {
        ReservationTimeResponse timeResponse = timeService.createTime(request);
        return ResponseEntity.ok().body(timeResponse);
    }

    @GetMapping("/reservationTimes")
    public ResponseEntity<List<ReservationTimeResponse>> getAll() {
        List<ReservationTimeResponse> allTimes = timeService.getAllTimes();
        return ResponseEntity.ok().body(allTimes);
    }

    @GetMapping("/reservationTimes/available")
    public ResponseEntity<List<ReservationTimeAvailableResponse>> getAvailableTimes(
            @RequestBody ReservationTimeAvailableRequest request
            ) {
        List<ReservationTimeAvailableResponse> availableTimes = timeService.findAvailableTimes(request);
        return ResponseEntity.ok().body(availableTimes);
    }
}
