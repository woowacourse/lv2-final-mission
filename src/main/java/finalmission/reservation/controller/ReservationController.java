package finalmission.reservation.controller;

import finalmission.member.domain.Member;
import finalmission.reservation.dto.EditRequest;
import finalmission.reservation.dto.MyReservationResponse;
import finalmission.reservation.dto.ReservationRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.service.ReservationService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    private ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request, Member member) {
        return ResponseEntity.ok(reservationService.addReservation(request, member));
    }

    @GetMapping
    private ResponseEntity<List<ReservationResponse>> getReservations() {
        return ResponseEntity.ok(reservationService.getAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<MyReservationResponse> getMemberReservationDetails(@PathVariable("id") Long id, Member member) {
        return ResponseEntity.ok(reservationService.getMemberReservation(id, member.getId()));
    }

    @PutMapping("/{id}")
    private ResponseEntity<MyReservationResponse> editReservation(@PathVariable("id") Long id, @RequestBody EditRequest request, Member member) {
        return ResponseEntity.ok(reservationService.editReservation(id, request, member.getId()));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id, Member member) {
        reservationService.deleteReservation(id, member.getId());
        return ResponseEntity.ok().build();
    }
}
