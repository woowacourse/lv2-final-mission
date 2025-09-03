package finalmission.controller;

import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.dto.MakingReservationRequest;
import finalmission.service.ReservationService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    ResponseEntity<List<Reservation>> getAllReservations() {

        return ResponseEntity.ok().body(reservationService.findAllReservations());
    }

    @PostMapping
    ResponseEntity<Reservation> createReservation(@RequestBody MakingReservationRequest request, Member member) {

        return ResponseEntity.created(URI.create("/reservation")).body(reservationService.createReservation(request,member));
    }

    @GetMapping("/mine")
    ResponseEntity<List<Reservation>> myReservations(Member member) {

        return ResponseEntity.ok().body(reservationService.findMyReservation(member));
    }

    @DeleteMapping
    ResponseEntity<Void> deleteMyReservation(@RequestBody Long reservationId, Member member) {

        reservationService.deleteMyReservation(reservationId, member);

        return ResponseEntity.ok().build();
    }
}
