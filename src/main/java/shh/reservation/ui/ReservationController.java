package shh.reservation.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shh.login.application.dto.LoginCheckRequest;
import shh.reservation.application.ReservationService;
import shh.reservation.application.dto.ReservationAddRequest;
import shh.reservation.application.dto.ReservationAddResponse;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reservations")
    public ResponseEntity<ReservationAddResponse> add(
            @Valid @RequestBody final ReservationAddRequest reservationAddRequest,
            final LoginCheckRequest loginCheckRequest
    ) {
        final ReservationAddResponse response = reservationService.reserve(loginCheckRequest.id(),
                reservationAddRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
