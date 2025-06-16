package finalmission.reservation.controller;

import finalmission.auth.controller.dto.request.LoginMember;
import finalmission.reservation.controller.dto.request.ReservationRequest;
import finalmission.reservation.controller.dto.response.ReservationResponse;
import finalmission.reservation.domain.Reservation;
import finalmission.mail.ReservationMailSender;
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
    private final ReservationMailSender reservationMailSender;

    public ReservationController(final ReservationService reservationService, final ReservationMailSender reservationMailSender) {
        this.reservationService = reservationService;
        this.reservationMailSender = reservationMailSender;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> readAll() throws IOException {
        List<Reservation> reservations = reservationService.readAll();
        List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody @Valid final ReservationRequest request, final LoginMember loginMember) {
        Reservation reservation = reservationService.create(request,loginMember);
        reservationMailSender.sendReservationMail(reservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(ReservationResponse.from(reservation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> update(
            @PathVariable final long id,
            @RequestBody final ReservationRequest request,
            final LoginMember loginMember ) {

        ReservationResponse reservationResponse = reservationService.update(id, request, loginMember);
        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id, final LoginMember loginMember ) {
        reservationService.delete(id,loginMember);
        return ResponseEntity.noContent().build();
    }
}
