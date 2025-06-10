package finalmission.reservation.ui;

import static finalmission.auth.domain.AuthRole.ADMIN;
import static finalmission.auth.domain.AuthRole.MEMBER;

import finalmission.auth.domain.MemberAuthInfo;
import finalmission.auth.domain.RequiresRole;
import finalmission.reservation.application.ReservationService;
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
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationRestController {

    private final ReservationService reservationService;

    @PostMapping
    @RequiresRole(authRoles = {ADMIN, MEMBER})
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody @Valid final CreateReservationRequest request,
            final MemberAuthInfo memberAuthInfo
    ) {
        final ReservationResponse response =
                reservationService.create(request, memberAuthInfo.id());

        return ResponseEntity.created(URI.create("/reservations/" + response.restaurantId())).body(response);
    }

    @DeleteMapping("/{id}")
    @RequiresRole(authRoles = {ADMIN, MEMBER})
    public ResponseEntity<Void> deleteReservation(
            @PathVariable final Long id,
            final MemberAuthInfo memberAuthInfo
    ) {
        reservationService.deleteIfOwner(id, memberAuthInfo.id());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mine")
    @RequiresRole(authRoles = {ADMIN, MEMBER})
    public ResponseEntity<List<ReservationResponse>> findAllMyReservations(
            final MemberAuthInfo memberAuthInfo
    ) {
        return ResponseEntity.ok()
                .body(reservationService.findReservationsByMemberId(memberAuthInfo.id()));
    }
}
