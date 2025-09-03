package finalmission.controller;

import finalmission.dto.request.AddWaitingRequest;
import finalmission.dto.response.AddWaitingResponse;
import finalmission.dto.response.RankResponse;
import finalmission.infra.auth.AuthenticationPrincipal;
import finalmission.infra.auth.LoginMember;
import finalmission.service.ReservationService;
import finalmission.service.WaitingLineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/waiting")
public class WaitingController {

    private final ReservationService reservationService;
    private final WaitingLineService waitingLineService;

    public WaitingController(final ReservationService reservationService, final WaitingLineService waitingLineService) {
        this.reservationService = reservationService;
        this.waitingLineService = waitingLineService;
    }

    @PostMapping
    public AddWaitingResponse addWaiting(@RequestBody AddWaitingRequest addWaitingRequest, @AuthenticationPrincipal
    LoginMember loginMember) {
        return reservationService.addWaiting(addWaitingRequest, loginMember);
    }

    @GetMapping("/{id}")
    public RankResponse getMyRank(@PathVariable Long id, @AuthenticationPrincipal LoginMember loginMember) {
        return waitingLineService.findRankById(id, loginMember);
    }
}
