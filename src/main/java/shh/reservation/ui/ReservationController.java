package shh.reservation.ui;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shh.login.application.dto.LoginCheckRequest;
import shh.reservation.application.ReservationService;
import shh.reservation.application.dto.MyReservationResponse;
import shh.reservation.application.dto.ReservationAddRequest;
import shh.reservation.application.dto.ReservationAddResponse;
import shh.reservation.application.dto.ReservationResponse;
import shh.reservation.application.dto.ReservationUpdateRequest;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reservations")
    public ResponseEntity<ReservationAddResponse> addReservation(
            @Valid @RequestBody final ReservationAddRequest reservationAddRequest,
            final LoginCheckRequest loginCheckRequest
    ) {
        final ReservationAddResponse response = reservationService.reserve(loginCheckRequest.id(),
                reservationAddRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/mine")
    public ResponseEntity<List<MyReservationResponse>> findMyReservation(final LoginCheckRequest loginCheckRequest) {
        final List<MyReservationResponse> response = reservationService.findMyReservation(loginCheckRequest.id());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<Void> updateReservation(
            final LoginCheckRequest loginCheckRequest,
            @PathVariable("id") final Long id,
            @RequestBody final ReservationUpdateRequest updateReservationRequest
    ) {
        reservationService.updateDateAndTime(loginCheckRequest.id(), id, updateReservationRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(
            final LoginCheckRequest loginCheckRequest,
            @PathVariable("id") final Long id
    ) {
        reservationService.deleteReservation(loginCheckRequest.id(), id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<List<ReservationResponse>> findReservationById(
            @PathVariable("id") final Long stallId
    ) {
        final List<ReservationResponse> responses = reservationService.findReservationByStallId(stallId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
