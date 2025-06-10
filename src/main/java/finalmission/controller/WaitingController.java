package finalmission.controller;

import finalmission.dto.request.AddWaitingRequest;
import finalmission.dto.response.AddWaitingResponse;
import finalmission.infra.auth.AuthenticationPrincipal;
import finalmission.infra.auth.LoginMember;
import finalmission.service.ReservationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/waiting")
public class WaitingController {

    private final ReservationService reservationService;

    public WaitingController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public AddWaitingResponse addWaiting(@RequestBody AddWaitingRequest addWaitingRequest, @AuthenticationPrincipal
    LoginMember loginMember) {
        return reservationService.addWaiting(addWaitingRequest, loginMember);
    }
}
