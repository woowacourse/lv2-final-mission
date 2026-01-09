package finalmission.member;

import finalmission.auth.AuthMember;
import finalmission.reservation.ReservationResponse;
import finalmission.reservation.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final ReservationService reservationService;

    @GetMapping("/member/reservations")
    public ResponseEntity<List<ReservationResponse>> getMyReservations(@AuthMember Member member) {
        List<ReservationResponse> responses = reservationService.getByMember(member);
        return ResponseEntity.ok().body(responses);
    }
}
