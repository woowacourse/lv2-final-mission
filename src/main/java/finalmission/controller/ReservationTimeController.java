package finalmission.controller;

import finalmission.domain.ReservationTime;
import finalmission.dto.MakingReservationTimeRequest;
import finalmission.service.ReservationTimeService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation_time")
public class ReservationTimeController {

    @Autowired
    ReservationTimeService reservationTimeService;

    @GetMapping
    ResponseEntity<List<ReservationTime>> getAllReservationTimes() {
        return ResponseEntity.ok().body(reservationTimeService.findAllReservationTimes());
    }

    @PostMapping
    ResponseEntity<ReservationTime> createReservation(@RequestBody MakingReservationTimeRequest request) {
        return ResponseEntity.created(URI.create("/reservation_time")).body(reservationTimeService.create(request));
    }
}
