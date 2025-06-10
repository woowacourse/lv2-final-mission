package finalmission.presentation;

import finalmission.application.ReservationService;
import finalmission.application.dto.request.CreateReservationRequest;
import finalmission.application.dto.response.CreateReservationResponse;
import finalmission.presentation.support.methodresolver.AuthInfo;
import finalmission.presentation.support.methodresolver.AuthPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/refrigerator/{refrigeratorId}/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<CreateReservationResponse> createReservation(
            @PathVariable String refrigeratorId,
            @Valid @RequestBody CreateReservationRequest createReservationRequest,
            @AuthPrincipal AuthInfo authInfo
    ) {
        CreateReservationResponse createReservationResponse = reservationService.reserve(
                authInfo.memberId(),
                refrigeratorId,
                createReservationRequest
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createReservationResponse);
    }
}
