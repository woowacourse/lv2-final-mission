package finalmission.controller;

import finalmission.annotation.CheckRole;
import finalmission.domain.MemberRole;
import finalmission.dto.request.CreateReservationRequest;
import finalmission.dto.request.MemberInfo;
import finalmission.dto.request.MyReservationResponse;
import finalmission.dto.response.CreateReservationResponse;
import finalmission.dto.response.ReservationResponse;
import finalmission.service.HolidayService;
import finalmission.service.ReservationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final HolidayService holidayService;

    public ReservationController(final ReservationService reservationService, final HolidayService holidayService) {
        this.reservationService = reservationService;
        this.holidayService = holidayService;
    }

    @CheckRole({MemberRole.ADMIN})
    @GetMapping
    public List<ReservationResponse> getAllReservation() {
        return reservationService.findAllReservation();
    }

    @CheckRole({MemberRole.MEMBER, MemberRole.ADMIN})
    @GetMapping("/mine")
    public List<MyReservationResponse> getAllMyReservation(MemberInfo memberInfo) {
        return reservationService.findAllMyReservation(memberInfo);
    }

    @CheckRole({MemberRole.MEMBER, MemberRole.ADMIN})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateReservationResponse addReservation(@Valid @RequestBody CreateReservationRequest request,
                                                    MemberInfo memberInfo) {
        holidayService.checkHoliday(request.date());
        return reservationService.addReservation(request, memberInfo);
    }

    @CheckRole({MemberRole.MEMBER, MemberRole.ADMIN})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable("id") Long id, MemberInfo memberInfo) {
        reservationService.deleteReservation(id, memberInfo);
    }
}
