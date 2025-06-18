package finalmission.controller;

import finalmission.auth.Authenticated;
import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.dto.request.ReservationRequest;
import finalmission.dto.request.ReservationUpdateRequest;
import finalmission.dto.response.ReservationResponse;
import finalmission.service.MemberService;
import finalmission.service.ReservationService;
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
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final MemberService memberService;

    public ReservationController(final ReservationService reservationService, final MemberService memberService) {
        this.reservationService = reservationService;
        this.memberService = memberService;
    }

    @GetMapping("/my")
    public List<ReservationResponse> getMyReservations(@Authenticated Long memberId) {
        List<Reservation> reservations = reservationService.findReservationsByMemberId(memberId);
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Authenticated Long memberId,
            @RequestBody ReservationRequest request) {
        Member member = memberService.findMemberById(memberId);
        Reservation reservation = reservationService.createReservation(request, member);

        ReservationResponse response = ReservationResponse.from(reservation);

        return ResponseEntity.created(URI.create("/api/reservation" + reservation.getId())).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateMyReservation(
            @PathVariable Long id,
            @Authenticated Long memberId,
            @RequestBody ReservationUpdateRequest request) {
        Reservation reservation = reservationService.updateReservation(id, memberId, request);
        ReservationResponse response = ReservationResponse.from(reservation);

        return ResponseEntity.created(URI.create("/api/reservation" + reservation.getId())).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMyReservation(
            @PathVariable Long id,
            @Authenticated Long memberId) {
        reservationService.deleteReservation(id, memberId);
        return ResponseEntity.noContent().build();
    }
}
