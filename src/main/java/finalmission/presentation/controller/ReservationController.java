package finalmission.presentation.controller;

import finalmission.dto.ReservationDetailResponseDto;
import finalmission.dto.ReservationRegisterDto;
import finalmission.dto.ReservationResponseDto;
import finalmission.dto.ReservationUpdateDto;
import finalmission.model.Member;
import finalmission.presentation.service.ReservationService;
import finalmission.service.AuthenticatedMember;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
                                    @AuthenticatedMember Member member) {
        reservationService.registerReservation(reservationRegisterDto, member);
    }

    @GetMapping("/mine")
    public List<ReservationResponseDto> getMyReservations(@AuthenticatedMember Member member) {
        return reservationService.getMyReservations(member);
    }

    @GetMapping("/{reservationId}")
    public ReservationDetailResponseDto getReservationInDetail(@PathVariable Long reservationId,
                                                               @AuthenticatedMember Member member) {
        return reservationService.getReservation(reservationId, member);
    }

    @PutMapping("/{reservationId}")
    public void updateReservation(@PathVariable Long reservationId,
                                  @RequestBody ReservationUpdateDto reservationUpdateDto,
                                  @AuthenticatedMember Member member) {
        reservationService.updateReservation(reservationId, reservationUpdateDto, member);
    }

    @DeleteMapping("/{reservationId}")
    public void deleteReservation(@PathVariable Long reservationId, @AuthenticatedMember Member member) {
        reservationService.deleteReservation(reservationId, member);
    }
}
