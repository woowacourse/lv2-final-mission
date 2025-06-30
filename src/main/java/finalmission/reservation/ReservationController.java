package finalmission.reservation;

import java.util.List;
import finalmission.auth.Auth;
import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Auth Member member, @RequestBody final ReservationRequest request) {
        Reservation createdReservation = reservationService.createReservation(member, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readAllReservation() {
        List<Reservation> reservations = reservationService.readAllReservation();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@Auth Member member, @PathVariable("id") final Long id) {
        reservationService.deleteReservation(member, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateReservation(
            @Auth Member member,
            @PathVariable("id") final Long id,
            @RequestBody final ReservationRequest request
    ) {
        reservationService.replaceReservation(member, id, request);
        return ResponseEntity.ok().build();
    }
}
