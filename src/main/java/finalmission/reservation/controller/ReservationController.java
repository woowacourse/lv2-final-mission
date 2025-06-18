package finalmission.reservation.controller;

import finalmission.auth.dto.LoginMember;
import finalmission.reservation.dto.MyReservationResponse;
import finalmission.reservation.dto.ReservationRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.service.ReservationService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<ReservationResponse> create(LoginMember loginMember,
                                                      @RequestBody ReservationRequest request) {
        ReservationResponse response = reservationService.create(loginMember, request);
        return ResponseEntity.created(URI.create("/reservations/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> response = reservationService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/mine")
    public ResponseEntity<List<MyReservationResponse>> getAllMyReservations(LoginMember loginMember) {
        List<MyReservationResponse> allMyReservations = reservationService.findAllMyReservations(loginMember);
        return ResponseEntity.ok().body(allMyReservations);
    }

    @PatchMapping("/mine/{reservationId}")
    public ResponseEntity<ReservationResponse> updateReservation(LoginMember loginMember,
                                                                 @PathVariable("reservationId") Long reservationId,
                                                                 @RequestBody ReservationRequest request) {
        ReservationResponse response = reservationService.update(loginMember, reservationId, request);
        return ResponseEntity.created(URI.create("/reservations/mine/" + response.id())).body(response);
    }

    @DeleteMapping("/mine/{reservationId}")
    public ResponseEntity<Void> deleteMyReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.delete(reservationId);
        return ResponseEntity.noContent().build();
    }
}
