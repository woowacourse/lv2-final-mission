package finalmission.planning.ui;

import finalmission.planning.application.ReservationService;
import finalmission.planning.auth.ui.CurrentUser;
import finalmission.planning.auth.ui.dto.CurrentUserInfo;
import finalmission.planning.ui.dto.request.CreateReservationRequest;
import finalmission.planning.ui.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReservationResponse createReservation(@RequestBody CreateReservationRequest request,
                                                 @CurrentUser CurrentUserInfo currentUserInfo) {
        return reservationService.registerReservation(request, currentUserInfo);
    }
}
