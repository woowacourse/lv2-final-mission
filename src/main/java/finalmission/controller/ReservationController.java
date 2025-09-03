package finalmission.controller;

import finalmission.auth.Authenticated;
import finalmission.dto.ReservationCreateRequest;
import finalmission.dto.ReservationDetailResponse;
import finalmission.dto.ReservationResponse;
import finalmission.dto.ReservationUpdateRequest;
import finalmission.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAllByMemberId(@Authenticated Long memberId) {
        List<ReservationResponse> response = reservationService.findAllMemberReservations(memberId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Authenticated Long memberId,
                                                                 @RequestBody ReservationCreateRequest request) {
        ReservationResponse response = reservationService.createMemberReservation(memberId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDetailResponse> findReservationDetail(@PathVariable("reservationId") Long reservationId) {
        ReservationDetailResponse response = reservationService.findMemberReservationDetail(reservationId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{reservationId}")
    public ResponseEntity<ReservationResponse> updateReservationInfo(@PathVariable("reservationId") Long reservationId,
                                                                     @RequestBody ReservationUpdateRequest request) {
        ReservationResponse response = reservationService.updateMemberReservation(reservationId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> removeReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteMemberReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
