package finalmission.controller;

import finalmission.domain.login.LoginMember;
import finalmission.domain.member.Member;
import finalmission.domain.reservation.Reservation;
import finalmission.dto.AcceptResultDto;
import finalmission.dto.ReservationRequestDto;
import finalmission.dto.ReservationResponse;
import finalmission.service.ReservationService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


// TODO 1순위 : Crew와 Coach의 API 재사용 방법 생각해보기
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // TODO : API Endpoint 변경하기
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/crew-reservation")
    public Reservation save(@RequestBody ReservationRequestDto reservationRequestDto, @LoginMember Member member) {
        return reservationService.save(reservationRequestDto, member);
    }

    // TODO : API Endpoint 변경하기
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/coach-reservations/{reservationId}")
    public void deleteFromCoach(
        @PathVariable Long reservationId, @LoginMember Member member) {
        reservationService.deleteFromCoach(reservationId, member);
    }

    // TODO : API Endpoint 변경하기
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/crew-reservations/{reservationId}")
    public void deleteFromCrew(@PathVariable Long reservationId, @LoginMember Member member) {
        reservationService.deleteFromCrew(reservationId, member);
    }

    // TODO : API Endpoint 변경하기
    @GetMapping("/crews/{crewId}/reservations")
    public List<ReservationResponse> getAllFromCrew(@PathVariable("crewId") Long crewId, @LoginMember Member member) {
        return reservationService.getAllReservationsFromCrewId(crewId, member);
    }

    // TODO : API Endpoint 변경하기
    @GetMapping("/coaches/{coachId}/reservations")
    public List<ReservationResponse> getAllFromCoach(@PathVariable Long coachId, @LoginMember Member member) {
        return reservationService.getAllReservationsFromCoachId(coachId, member);
    }

    @PostMapping("/coach-reservations/{reservationId}/accept")
    public void acceptReservation(
        @PathVariable Long reservationId,
        @RequestBody AcceptResultDto resultDto,
        @LoginMember Member member
        ) {
        reservationService.accept(reservationId, resultDto, member);
    }
}
