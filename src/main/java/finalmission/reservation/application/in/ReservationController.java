package finalmission.reservation.application.in;

import finalmission.reservation.application.ReservationService;
import finalmission.reservation.application.in.dto.Reserve;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Void> reserve(@RequestBody Reserve request) {
        reservationService.reserve(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
