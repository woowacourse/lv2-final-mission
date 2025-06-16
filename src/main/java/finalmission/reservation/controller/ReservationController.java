package finalmission.reservation.controller;

import finalmission.auth.controller.dto.request.LoginMember;
import finalmission.reservation.service.ReservationMailService;
import finalmission.reservation.controller.dto.request.ReservationRequest;
import finalmission.reservation.controller.dto.response.ReservationResponse;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationMailService reservationMailService;

    public ReservationController(final ReservationService reservationService, final ReservationMailService reservationMailService) {
        this.reservationService = reservationService;
        this.reservationMailService = reservationMailService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody @Valid final ReservationRequest request, final LoginMember loginMember) {
        Reservation reservation = reservationService.create(request,loginMember);
        reservationMailService.sendReservationMail(reservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(ReservationResponse.from(reservation));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> readAll() throws IOException {
        List<Reservation> reservations = reservationService.readAll();
        List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok().body(responses);
    }
}
