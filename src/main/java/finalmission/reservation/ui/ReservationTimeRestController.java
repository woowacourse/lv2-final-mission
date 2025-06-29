package finalmission.reservation.ui;

import static finalmission.auth.domain.AuthRole.ADMIN;

import finalmission.auth.domain.RequiresRole;
import finalmission.reservation.application.ReservationTimeService;
import finalmission.reservation.ui.dto.CreateReservationTimeRequest;
import finalmission.reservation.ui.dto.ReservationTimeResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class ReservationTimeRestController {

    private final ReservationTimeService reservationTimeService;

    @PostMapping
    @RequiresRole(authRoles = {ADMIN})
    public ResponseEntity<ReservationTimeResponse> create(
            @RequestBody @Valid final CreateReservationTimeRequest request
    ) {
        final ReservationTimeResponse response = reservationTimeService.create(request);

        return ResponseEntity.created(URI.create("/times/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/{id}")
    @RequiresRole(authRoles = {ADMIN})
    public ResponseEntity<Void> delete(
            @PathVariable final Long id
    ) {
        reservationTimeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> findAll() {
        return ResponseEntity.ok(reservationTimeService.findAll());
    }
}
