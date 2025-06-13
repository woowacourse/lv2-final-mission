package finalmission.reservation.controller;

import finalmission.auth.LoginMember;
import finalmission.auth.LoginMemberInfo;
import finalmission.reservation.dto.CreateReservationRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.dto.UpdateReservationResponse;
import finalmission.reservation.service.HolidayService;
import finalmission.reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final HolidayService holidayService;

    public ReservationController(final ReservationService reservationService, final HolidayService holidayService) {
        this.reservationService = reservationService;
        this.holidayService = holidayService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody CreateReservationRequest reservationRequest,
            @LoginMember LoginMemberInfo loginMemberInfo) {
        holidayService.validateHoliday(reservationRequest.reservationDateTime());
        ReservationResponse reservationResponse = reservationService.save(reservationRequest, loginMemberInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAll() {
        List<ReservationResponse> reservationResponses = reservationService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(reservationResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> findById(@PathVariable Long id) {
        ReservationResponse reservationResponse = reservationService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @LoginMember LoginMemberInfo loginMemberInfo) {;
        reservationService.deleteById(id, loginMemberInfo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservation(
            @PathVariable Long id,
            @RequestBody UpdateReservationResponse reservationRequest,
            @LoginMember LoginMemberInfo loginMemberInfo) {
        holidayService.validateHoliday(reservationRequest.reservationDateTime());
        ReservationResponse reservationResponse = reservationService.updateReservation(id, reservationRequest, loginMemberInfo);
        return ResponseEntity.status(HttpStatus.OK).body(reservationResponse);
    }

    @GetMapping("/member")
    public ResponseEntity<List<ReservationResponse>> getMemberReservation(@LoginMember LoginMemberInfo loginMemberInfo) {
        List<ReservationResponse> reservationResponses = reservationService.findByMemberId(loginMemberInfo);
        return ResponseEntity.status(HttpStatus.OK).body(reservationResponses);
    }
}
