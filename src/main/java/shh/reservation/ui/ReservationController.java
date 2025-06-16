package shh.reservation.ui;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shh.login.application.dto.LoginCheckRequest;
import shh.reservation.application.ReservationService;
import shh.reservation.application.dto.MyReservationResponse;
import shh.reservation.application.dto.ReservationAddRequest;
import shh.reservation.application.dto.ReservationAddResponse;

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
}
