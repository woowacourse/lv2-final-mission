package finalmission.controller;

import finalmission.dto.ReservationCreateRequest;
import finalmission.dto.ReservationResponse;
import finalmission.dto.ReservationUpdateRequest;
import finalmission.global.LoginMember;
import finalmission.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody final ReservationCreateRequest reservationCreateRequest, @LoginMember final Long memberId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.save(memberId,
                reservationCreateRequest));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations(@LoginMember final Long memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getReservationsByMemberId(memberId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservation(@RequestBody final ReservationUpdateRequest reservationRequest, @PathVariable(name = "id") final long reservationId, @LoginMember final Long memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.update(reservationId, memberId, reservationRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable(name = "id") final long reservationId, @LoginMember final Long memberId) {
        reservationService.delete(memberId, reservationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
