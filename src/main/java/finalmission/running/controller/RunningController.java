package finalmission.running.controller;

import finalmission.member.auth.argumentresolver.LoginMember;
import finalmission.member.dto.response.LoginInfo;
import finalmission.running.dto.request.ReservationRequest;
import finalmission.running.dto.response.ReservationResponse;
import finalmission.running.service.RunningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RunningController {

    private final RunningService runningService;

    @PostMapping("/runningSessions")
    public ResponseEntity<ReservationResponse> createRunningSession(
        @RequestBody ReservationRequest request,
        @LoginMember LoginInfo loginInfo) {
        return ResponseEntity.ok(runningService.createRunningReservation(request, loginInfo));
    }
}
