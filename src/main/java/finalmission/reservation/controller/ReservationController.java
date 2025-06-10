package finalmission.reservation.controller;

import finalmission.auth.MemberSubject;
import finalmission.auth.RequiredOwner;
import finalmission.reservation.service.ReservationService;
import finalmission.reservation.service.dto.CreateReservationInformationRequest;
import finalmission.reservation.service.dto.CreateReservationInformationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservation-info")
    @RequiredOwner
    public ResponseEntity<CreateReservationInformationResponse> uploadReservationInfo(
            @MemberSubject Long memberId,
            @RequestBody @Valid CreateReservationInformationRequest request
    ) {
        CreateReservationInformationResponse response = reservationService.createReservationInformation(request, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
