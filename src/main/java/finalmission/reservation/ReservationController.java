package finalmission.reservation;

import finalmission.auth.AuthMember;
import finalmission.member.Member;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reservations")
    public ResponseEntity<Void> create(
        @AuthMember Member member,
        @RequestBody ReservationRequest request) {
        reservationService.create(member, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<MeetingRoomTimeResponse>> getAvailableTime(
        @RequestParam("meetingroom") Long meetingRoomId,
        @RequestParam("date") LocalDate date
    ) {
        List<MeetingRoomTimeResponse> responses = reservationService.getAvailableTimes(
            meetingRoomId, date);
        return ResponseEntity.ok().body(responses);
    }

    @DeleteMapping("/reservations")
    public ResponseEntity<Void> delete(@AuthMember Member member,
        @RequestParam("reservationId") Long reservationId) {
        reservationService.delete(member, reservationId);
        return ResponseEntity.ok().build();
    }
}
