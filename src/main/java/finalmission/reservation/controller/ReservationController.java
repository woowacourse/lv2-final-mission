package finalmission.reservation.controller;

import finalmission.common.security.RequireRole;
import finalmission.member.domain.MemberInfo;
import finalmission.member.domain.MemberRole;
import finalmission.reservation.dto.MyReservationResponse;
import finalmission.reservation.dto.ReservationCreateRequest;
import finalmission.reservation.dto.ReservationCreateResponse;
import finalmission.reservation.dto.ReservationDeleteRequest;
import finalmission.reservation.dto.ReservationModifyRequest;
import finalmission.reservation.dto.ReservationModifyResponse;
import finalmission.reservation.dto.ReservationSearchRequest;
import finalmission.reservation.dto.SearchReservationResponse;
import finalmission.reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @RequireRole(MemberRole.REGULAR)
    @PostMapping
    public ResponseEntity<ReservationCreateResponse> createReservation(final MemberInfo memberInfo,
                                                                       @RequestBody ReservationCreateRequest request) {
        ReservationCreateResponse response = reservationService.createReservation(memberInfo, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<SearchReservationResponse> searchReservations(@RequestBody ReservationSearchRequest request) {
        SearchReservationResponse response = reservationService.searchReservations(request);
        return ResponseEntity.ok().body(response);
    }

    @RequireRole(MemberRole.REGULAR)
    @GetMapping("/mine")
    public ResponseEntity<MyReservationResponse> findMyReservations(final MemberInfo memberInfo) {
        MyReservationResponse response = reservationService.findMyReservations(memberInfo.memberId());
        return ResponseEntity.ok(response);
    }

    @RequireRole(MemberRole.REGULAR)
    @PutMapping
    public ResponseEntity<ReservationModifyResponse> modifyReservation(final MemberInfo memberInfo,
                                                                       @RequestBody ReservationModifyRequest request) {
        ReservationModifyResponse response = reservationService.modifyReservation(memberInfo, request);
        return ResponseEntity.ok().body(response);
    }

    @RequireRole(MemberRole.REGULAR)
    @DeleteMapping
    public ResponseEntity<ReservationModifyResponse> deleteReservation(final MemberInfo memberInfo,
                                                                       @RequestBody ReservationDeleteRequest request) {
        reservationService.deleteReservation(memberInfo, request);
        return ResponseEntity.noContent().build();
    }
}
