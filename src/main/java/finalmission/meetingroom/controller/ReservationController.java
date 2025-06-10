package finalmission.meetingroom.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finalmission.meetingroom.service.ReservationService;
import finalmission.meetingroom.service.request.LoginMember;
import finalmission.meetingroom.service.request.ReservationCreateRequest;
import finalmission.meetingroom.service.response.ReservationResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getMeetingRoomReservations() {
        List<ReservationResponse> responses = reservationService.getReservations();
        return ResponseEntity.ok()
                .body(responses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createMeetingRoomReservation(
            @RequestBody final ReservationCreateRequest request,
            final LoginMember loginMember
    ) {
        ReservationResponse response = reservationService.reserveMeetingRoom(request, loginMember);
        return ResponseEntity.created(URI.create("/reservations/" + response.reservationId()))
                .body(response);
    }
}
