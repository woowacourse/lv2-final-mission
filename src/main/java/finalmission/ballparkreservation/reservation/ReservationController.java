package finalmission.ballparkreservation.reservation;

import finalmission.ballparkreservation.auth.MemberAuthentication;
import finalmission.ballparkreservation.auth.dto.LoginMember;
import finalmission.ballparkreservation.reservation.dto.ReservationCreateRequest;
import finalmission.ballparkreservation.reservation.dto.ReservationCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationCreateResponse> create(
            @Valid @RequestBody ReservationCreateRequest request,
            @MemberAuthentication LoginMember member
    ) {
        ReservationCreateResponse response = reservationService.create(request, member);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
