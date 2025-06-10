package finalmission.presentation.controller;

import finalmission.dto.LoginMember;
import finalmission.dto.ReservationRegisterDto;
import finalmission.presentation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public void registerReservation(@RequestBody ReservationRegisterDto reservationRegisterDto,
                                    LoginMember loginMember) {
        reservationService.registerReservation(reservationRegisterDto, loginMember);
    }
}
