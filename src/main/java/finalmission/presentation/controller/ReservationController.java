package finalmission.presentation.controller;

import finalmission.application.dto.ReservationRequest;
import finalmission.application.dto.ReservationResponse;
import finalmission.application.service.ReservationService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(
            final @RequestBody @Valid ReservationRequest reservationRequest
    ) {
        return ResponseEntity.created(createUri(reservationService.createReservation(reservationRequest))).build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations(
            final @RequestParam String crew
    ){
        return ResponseEntity.ok().body(
                reservationService.getReservations(crew)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservations(
            final @PathVariable Long id,
            final @RequestBody @Valid ReservationRequest reservationRequest
    ) {
        return ResponseEntity.ok().body(
                reservationService.updateReservation(id, reservationRequest)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
            final @PathVariable Long id
    ) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    private URI createUri(Long reservationId) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservationId)
                .toUri();
    }
}
