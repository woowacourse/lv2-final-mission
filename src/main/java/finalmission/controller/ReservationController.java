package finalmission.controller;

import finalmission.controller.dto.ReservationDetailResponse;
import finalmission.controller.dto.ReservationRequest;
import finalmission.controller.dto.ReservationResponse;
import finalmission.controller.dto.ReservationResponses;
import finalmission.controller.dto.ReservationUpdateRequest;
import finalmission.domain.Member;
import finalmission.global.config.AuthenticationPrincipal;
import finalmission.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController implements ReservationControllerSwagger {

    private final ReservationService reservationService;

    @GetMapping
    public ReservationResponses getAllReservation() {
        return reservationService.getAllReservation();
    }

    @GetMapping("/{reservationId}")
    public ReservationDetailResponse getDetailReservation(
            @PathVariable Long reservationId,
            @AuthenticationPrincipal Member member
    ) {
        return reservationService.getDetailReservationById(reservationId, member);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse registerReservation(
            @AuthenticationPrincipal Member member,
            @RequestBody ReservationRequest request
    ) {
        return reservationService.registerReservation(member, request);
    }

    @PatchMapping("/{reservationId}")
    public ReservationResponse updateReservation(
            @PathVariable Long reservationId,
            @RequestBody ReservationUpdateRequest request,
            @AuthenticationPrincipal Member member
    ) {
        return reservationService.updateReservation(reservationId, request, member);
    }

    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(
            @PathVariable Long reservationId,
            @AuthenticationPrincipal Member member
    ) {
        reservationService.deleteReservation(reservationId, member);
    }
}
