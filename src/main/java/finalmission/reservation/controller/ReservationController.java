package finalmission.reservation.controller;

import finalmission.member.auth.annotation.LoginMember;
import finalmission.member.auth.annotation.RoleRequired;
import finalmission.member.auth.vo.MemberInfo;
import finalmission.member.domain.Role;
import finalmission.reservation.controller.dto.ReservationChangeRequest;
import finalmission.reservation.controller.dto.ReservationDetailResponse;
import finalmission.reservation.controller.dto.ReservationRequest;
import finalmission.reservation.controller.dto.ReservationResponse;
import finalmission.reservation.service.ReservationFrontService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationFrontService reservationFrontService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @LoginMember final MemberInfo memberInfo,
            @RequestBody final ReservationRequest request
    ) {
        return ResponseEntity.ok(reservationFrontService.create(memberInfo.id(), request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable final Long id) {
        return ResponseEntity.ok(reservationFrontService.get(id));
    }

    @RoleRequired(value = Role.ADMIN)
    @GetMapping
    public List<ReservationResponse> getAllReservation() {
        return reservationFrontService.getAll();
    }

    @GetMapping("/mine")
    public ResponseEntity<List<ReservationDetailResponse>> getReservationMine(@LoginMember final MemberInfo memberInfo) {
        return ResponseEntity.ok(reservationFrontService.getDetails(memberInfo.id()));
    }

    @PutMapping
    public ResponseEntity<ReservationResponse> updateReservation(
            @LoginMember final MemberInfo memberInfo,
            @RequestBody final ReservationChangeRequest request
    ) {
        return ResponseEntity.ok(reservationFrontService.update(memberInfo, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
            @LoginMember final MemberInfo memberInfo,
            @PathVariable final Long id
    ) {
        reservationFrontService.delete(memberInfo, id);
        return ResponseEntity.noContent().build();
    }
}
