package finalmission.reservation.controller;

import finalmission.member.controller.dto.request.MemberRequest;
import finalmission.member.controller.dto.response.MemberResponse;
import finalmission.member.domain.Member;
import finalmission.reservation.controller.dto.request.ReservationRequest;
import finalmission.reservation.controller.dto.response.ReservationResponse;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody @Valid ReservationRequest request) {
        Reservation reservation = reservationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ReservationResponse.from(reservation));
    }
}
