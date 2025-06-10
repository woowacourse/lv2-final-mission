package finalmission.reservation.ui;

import static finalmission.auth.domain.AuthRole.ADMIN;

import finalmission.auth.domain.RequiresRole;
import finalmission.reservation.application.AdminReservationService;
import finalmission.reservation.ui.dto.CreateReservationRequest;
import finalmission.reservation.ui.dto.ReservationResponse;
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
@RequestMapping("/admin/reservations")
@RequiresRole(authRoles = {ADMIN})
@RequiredArgsConstructor
public class AdminReservationRestController {

    private final AdminReservationService adminReservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @RequestBody @Valid final CreateReservationRequest request
    ) {
        final ReservationResponse response = adminReservationService.create(request);

        return ResponseEntity.created(URI.create("/admin/reservations/" + response.restaurantId()))
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsAdmin(
            @PathVariable final Long id
    ) {
        adminReservationService.deleteAsAdmin(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAllReservations() {
        final List<ReservationResponse> reservationResponses = adminReservationService.findAll();

        return ResponseEntity.ok(reservationResponses);
    }
}
