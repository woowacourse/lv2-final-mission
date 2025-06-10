package finalmission.reservation.controller;

import finalmission.global.AuthenticationPrincipal;
import finalmission.member.dto.LoginMember;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.service.MyReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyReservationController {

    private final MyReservationService myReservationService;

    public MyReservationController(MyReservationService myReservationService) {
        this.myReservationService = myReservationService;
    }

    @GetMapping("/reservations-mine")
    public ResponseEntity<List<ReservationResponse>> getMyReservations(@AuthenticationPrincipal LoginMember loginMember) {
        List<ReservationResponse> myReservations = myReservationService.getMyReservations(loginMember);
        return ResponseEntity.ok(myReservations);
    }
}
