package finalmission.controller;

import finalmission.annotation.CheckRole;
import finalmission.domain.HolidayClient;
import finalmission.domain.MemberRole;
import finalmission.dto.request.CreateReservationRequest;
import finalmission.dto.request.MemberInfo;
import finalmission.dto.response.CreateReservationResponse;
import finalmission.dto.response.ReservationResponse;
import finalmission.service.ReservationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final HolidayClient holidayClient;

    public ReservationController(final ReservationService reservationService, final HolidayClient holidayClient) {
        this.reservationService = reservationService;
        this.holidayClient = holidayClient;
    }

    @GetMapping
    public List<ReservationResponse> getAllReservation() {
        return reservationService.findAllReservation();
    }

    @CheckRole({MemberRole.MEMBER, MemberRole.ADMIN})
    @PostMapping
    public CreateReservationResponse addReservation(@Valid @RequestBody CreateReservationRequest request,
                                                    MemberInfo memberInfo) {
        holidayClient.checkHoliday(request.date());
        return reservationService.addReservation(request, memberInfo);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable("id") Long id, MemberInfo memberInfo) {
        reservationService.deleteReservation(id, memberInfo);
    }
}
