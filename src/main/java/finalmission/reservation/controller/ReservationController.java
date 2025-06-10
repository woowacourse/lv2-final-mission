package finalmission.reservation.controller;

import finalmission.reservation.controller.dto.request.ReservationRequest;
import finalmission.reservation.controller.dto.response.ReservationResponse;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.service.ConfirmationHolidayService;
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
    private final ConfirmationHolidayService confirmationHolidayService;

    public ReservationController(final ReservationService reservationService, final ConfirmationHolidayService confirmationHolidayService) {
        this.reservationService = reservationService;
        this.confirmationHolidayService = confirmationHolidayService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody @Valid ReservationRequest request) {
        Reservation reservation = reservationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ReservationResponse.from(reservation));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> readAll() throws IOException {
        boolean holiday = confirmationHolidayService.isHoliday();
        List<Reservation> reservations = reservationService.readAll();
        List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok().body(responses);
    }


    @GetMapping("/my")
    public ResponseEntity<List<ReservationResponse>> readAll(@RequestParam long memberId) {
        List<Reservation> reservations = reservationService.read(memberId);
        List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok().body(responses);
    }
}
