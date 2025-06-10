package finalmission.presentation.controller;

import finalmission.dto.LoginMember;
import finalmission.dto.ReservationRegisterDto;
import finalmission.dto.ReservationResponseDto;
import finalmission.presentation.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/mine")
    public List<ReservationResponseDto> getMyReservations(LoginMember loginMember) {
        return reservationService.getMyReservations(loginMember);
    }
}
