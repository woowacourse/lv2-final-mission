package finalmission.music.controller;

import finalmission.music.global.auth.LoginMember;
import finalmission.music.controller.dto.MyReservationResponse;
import finalmission.music.controller.dto.ReservationRequest;
import finalmission.music.controller.dto.ReservationResponse;
import finalmission.music.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> reserve(@RequestBody final ReservationRequest request,
                                                       @LoginMember final String memberName) {
        ReservationResponse response = reservationService.reserve(request.lotteryId(), request.address(), memberName);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/reservations/me")
    public ResponseEntity<List<MyReservationResponse>> getMyReservations(@LoginMember final String memberName) {
        return ResponseEntity.ok(reservationService.getMemberReservations(memberName));
    }

    @GetMapping("/reservations/{id}")
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<MyReservationResponse> getMyReservation(@PathVariable("id") final Long id,
                                                                  @LoginMember final String memberName) {
        MyReservationResponse memberReservation = reservationService.getMemberReservation(id, memberName);

        return ResponseEntity.ok(memberReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteMyReservation(@PathVariable("id") final Long id,
                                                    @LoginMember final String memberName) {
        reservationService.deleteMemberReservation(id, memberName);

        return ResponseEntity.noContent().build();
    }
}
