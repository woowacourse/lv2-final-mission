package woowaTable.reservation.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowaTable.reservation.application.ReservationService;
import woowaTable.reservation.application.dto.ReservationRequest;
import woowaTable.reservation.application.dto.ReservationResponse;
import woowaTable.user.application.dto.LoginCheckRequest;

@RestController
@RequiredArgsConstructor
public class CustomerReservationController {

    private final ReservationService reservationService;

    @PostMapping("/restaurants")
    public ResponseEntity<ReservationResponse> reserve(
            final LoginCheckRequest loginCheckRequest,
            @Valid @RequestBody final ReservationRequest request
    ) {
        final ReservationResponse response = reservationService.addReservation(loginCheckRequest, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
