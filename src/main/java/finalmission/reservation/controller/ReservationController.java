package finalmission.reservation.controller;

import finalmission.global.AuthenticationPrincipal;
import finalmission.member.dto.LoginMember;
import finalmission.reservation.dto.ReservationRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody ReservationRequest request,
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        ReservationResponse reservationResponse = reservationService.addReservation(request, loginMember);
        return ResponseEntity.ok().body(reservationResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservationsByMember(
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        List<ReservationResponse> reservationResponses = reservationService.findAllByMember(loginMember);
        return ResponseEntity.ok().body(reservationResponses);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long reservationId,
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        reservationService.deleteReservationById(reservationId, loginMember);
        return ResponseEntity.noContent().build();
    }
}
