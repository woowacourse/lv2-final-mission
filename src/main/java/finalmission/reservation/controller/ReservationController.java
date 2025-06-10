package finalmission.reservation.controller;

import finalmission.auth.MemberSubject;
import finalmission.auth.RequiredOwner;
import finalmission.reservation.service.ReservationService;
import finalmission.reservation.service.dto.CreateReservationInformationRequest;
import finalmission.reservation.service.dto.ReservationInformationResponse;
import finalmission.reservation.service.dto.CreateReservationRequest;
import finalmission.reservation.service.dto.ReservationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation/mine")
    public ResponseEntity<List<ReservationResponse>> getAllReservations(@MemberSubject Long memberId) {
        List<ReservationResponse> response = reservationService.getAllMyReservations(memberId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reservation-info")
    @RequiredOwner
    public ResponseEntity<ReservationInformationResponse> uploadReservationInfo(
            @MemberSubject Long memberId,
            @RequestBody @Valid CreateReservationInformationRequest request
    ) {
        ReservationInformationResponse response = reservationService.createReservationInformation(request, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/reservation")
    public ResponseEntity<ReservationResponse> createReservation(
            @MemberSubject Long memberId,
            @RequestBody @Valid CreateReservationRequest request
    ) {
        ReservationResponse response = reservationService.createReservation(request, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
