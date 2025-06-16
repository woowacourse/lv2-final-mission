package finalmission.reservation.controller;

import finalmission.auth.controller.dto.request.LoginMember;
import finalmission.reservation.controller.dto.response.ReservationResponse;
import finalmission.reservation.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/my")
public class ReservationMyController {

    private final Logger log = LoggerFactory.getLogger(ReservationMyController.class);

    private final ReservationService reservationService;

    public ReservationMyController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> readMyReservations(final LoginMember loginMember) {
        log.info("loginMember : {}", loginMember.email());
        log.info("loginMember : {}", loginMember.password());
        log.info("loginMember : {}", loginMember.id());

        List<ReservationResponse> list = reservationService.findReservationsByMemberId(loginMember.id()).stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }
}
