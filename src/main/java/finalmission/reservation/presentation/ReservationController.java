package finalmission.reservation.presentation;

import finalmission.common.resolver.LoginMember;
import finalmission.member.dto.LoginInfo;
import finalmission.reservation.dto.ReservationCreateRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@LoginMember LoginInfo loginInfo, @RequestBody ReservationCreateRequest reservationCreateRequest){
        ReservationResponse pendingReservation = reservationService.createPendingReservation(reservationCreateRequest, loginInfo.memberId());
        URI uri = URI.create("reservations/" + pendingReservation.id());
        return ResponseEntity.created(uri).body(pendingReservation);
    }
}
