package finalmission.reservation.controller;

import finalmission.global.auth.annotation.AuthenticationPrincipal;
import finalmission.global.auth.annotation.RoleRequired;
import finalmission.global.auth.dto.LoginMember;
import finalmission.member.entity.RoleType;
import finalmission.reservation.dto.request.ReservationCreateRequest;
import finalmission.reservation.dto.request.ReservationUpdateRequest;
import finalmission.reservation.dto.response.ReservationResponse;
import finalmission.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("reservations")
@Tag(name = "예약", description = "예약 관련 API")
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "예약 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", useReturnTypeSchema = true),
    })
    @RoleRequired(roleType = {RoleType.USER, RoleType.ADMIN})
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody @Valid ReservationCreateRequest request
    ) {
        ReservationResponse response = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "모든 예약 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
    })
    @RoleRequired(roleType = {RoleType.USER, RoleType.ADMIN})
    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAllReservations() {
        List<ReservationResponse> responses = reservationService.findAllReservations();
        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "나의 모든 예약 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
    })
    @RoleRequired(roleType = {RoleType.USER, RoleType.ADMIN})
    @GetMapping("/mine")
    public ResponseEntity<List<ReservationResponse>> findAllMyReservations(
            @Parameter(hidden = true) @AuthenticationPrincipal LoginMember loginMember
    ) {
        List<ReservationResponse> responses = reservationService.findAllMyReservation(loginMember.id());
        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "특정 예약 조회 (어드민 권한)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
    })
    @RoleRequired(roleType = RoleType.ADMIN)
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> findReservationById(
            @PathVariable("id") Long id
    ) {
        ReservationResponse response = reservationService.findReservationById(id);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "예약 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", useReturnTypeSchema = true),
    })
    @RoleRequired(roleType = {RoleType.USER, RoleType.ADMIN})
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateReservation(
            @PathVariable("id") Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
            @RequestBody @Valid ReservationUpdateRequest request
    ) {
        reservationService.updateReservation(id, loginMember.id(), request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "예약 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", useReturnTypeSchema = true),
    })
    @RoleRequired(roleType = {RoleType.USER, RoleType.ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable("id") Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal LoginMember loginMember
    ) {
        reservationService.deleteReservation(id, loginMember.id());
        return ResponseEntity.noContent().build();
    }
}
