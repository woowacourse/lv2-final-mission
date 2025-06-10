package finalmission.music.controller;

import finalmission.music.config.LoginMember;
import finalmission.music.controller.dto.MyReservationResponse;
import finalmission.music.controller.dto.ReservationRequest;
import finalmission.music.controller.dto.ReservationResponse;
import finalmission.music.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> reserve(@RequestBody final ReservationRequest request,
                                                       @LoginMember final String member) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.reserve(request, member));
    }

    @GetMapping("/reservations/me")
    public ResponseEntity<List<MyReservationResponse>> getMyReservations(@LoginMember final String member) {
        return ResponseEntity.ok(reservationService.getMemberReservations(member));
    }
}
