package finalmission.controller;

import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.dto.MakingReservationRequest;
import finalmission.service.ReservationService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping
    ResponseEntity<List<Reservation>> getAllReservations() {

        return ResponseEntity.ok().body(reservationService.findAllReservations());
    }

    @PostMapping
    ResponseEntity<Reservation> createReservaiton(@RequestBody MakingReservationRequest request, Member member){
        return ResponseEntity.created(URI.create("/reservation")).body(reservationService.createReservation(request,member));
    }
}
