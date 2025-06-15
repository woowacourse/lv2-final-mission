package finalmission.reservation.controller;

import finalmission.auth.MemberSubject;
import finalmission.auth.RequiredOwner;
import finalmission.reservation.service.ReservationService;
import finalmission.reservation.service.dto.request.CreateReservationInformationRequest;
import finalmission.reservation.service.dto.response.ReservationDetailResponse;
import finalmission.reservation.service.dto.response.ReservationInformationResponse;
import finalmission.reservation.service.dto.request.CreateReservationRequest;
import finalmission.reservation.service.dto.response.ReservationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/reservation/mine/{id}")
    public ResponseEntity<ReservationDetailResponse> getReservationDetail(
            @MemberSubject Long memberId,
            @PathVariable("id") Long id
    ) {
        ReservationDetailResponse response = reservationService.getMyReservationDetail(memberId, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<Void> deleteReservation(
            @MemberSubject Long memberId,
            @PathVariable("id") Long id
    ) {
        reservationService.deleteReservation(memberId, id);
        return ResponseEntity.ok().build();
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
