package finalmission.presentation.controller;

import finalmission.application.dto.ReservationDateTimeRequest;
import finalmission.application.dto.ReservationDateTimeResponse;
import finalmission.application.service.ReservationDateTimeService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/dateTimes")
public class ReservationDateTimeController {

    private final ReservationDateTimeService reservationDateTimeService;

    public ReservationDateTimeController(final ReservationDateTimeService reservationDateTimeService) {
        this.reservationDateTimeService = reservationDateTimeService;
    }

    @PostMapping
    public ResponseEntity<Void> createReservationDateTime(
            final @RequestBody @Valid ReservationDateTimeRequest reservationDateTimeRequest
    ) {
        return ResponseEntity.created(
                createUri(reservationDateTimeService.createReservationDateTime(reservationDateTimeRequest))).build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationDateTimeResponse>> getReservationDateTimes(
            final @RequestParam String nickname
    ) {
        return ResponseEntity.ok().body(
                reservationDateTimeService.getReservationDateTimes(nickname)
        );
    }

    private URI createUri(Long reservationId) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservationId)
                .toUri();
    }
}