package lavatoryreservation.reservation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lavatoryreservation.external.auth.MemberAuthentication;
import lavatoryreservation.external.auth.MemberInfo;
import lavatoryreservation.reservation.domain.Reservation;
import lavatoryreservation.reservation.dto.AddReservationDto;
import lavatoryreservation.reservation.dto.DeleteReservationDto;
import lavatoryreservation.reservation.dto.ReservationSpecificDto;
import lavatoryreservation.reservation.service.ReservationService;

@RestController
@RequestMapping("/api/reservation/")
@Tag(name = "예약 관리", description = "화장실 예약 생성, 조회, 삭제 관련 API")
@SecurityRequirement(name = "JWT")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "예약 생성", description = "새로운 화장실 예약을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 생성 성공",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 화장실"),
            @ApiResponse(responseCode = "409", description = "예약 시간 충돌")
    })
    @PostMapping
    public ResponseEntity<Long> addReservation(
            @Parameter(description = "예약 생성 정보", required = true)
            @RequestBody AddReservationDto addReservationDto,
            @Parameter(description = "인증된 회원 정보", hidden = true)
            @MemberInfo MemberAuthentication memberAuthentication) {
        AddReservationDto memberAddReservationDto = new AddReservationDto(memberAuthentication.id(),
                addReservationDto.toiletId(), addReservationDto.startTime(), addReservationDto.endTime());

        Long reservationId = reservationService.addReservation(memberAddReservationDto);
        return ResponseEntity.ok(reservationId);
    }

    @Operation(summary = "예약 삭제", description = "기존 예약을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (본인 예약만 삭제 가능)"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 예약")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteReservation(
            @Parameter(description = "예약 삭제 정보", required = true)
            @RequestBody DeleteReservationDto deleteReservationDto,
            @Parameter(description = "인증된 회원 정보", hidden = true)
            @MemberInfo MemberAuthentication memberAuthentication) {
        reservationService.deleteReservation(deleteReservationDto, memberAuthentication.id());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "내 예약 조회", description = "현재 로그인한 회원의 예약 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 조회 성공",
                    content = @Content(schema = @Schema(implementation = ReservationSpecificDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "예약 정보 없음")
    })
    @GetMapping("/my")
    public ResponseEntity<ReservationSpecificDto> myReservation(
            @Parameter(description = "인증된 회원 정보", hidden = true)
            @MemberInfo MemberAuthentication memberAuthentication) {
        Reservation reservation = reservationService.myReservation(memberAuthentication.id());
        return ResponseEntity.ok(ReservationSpecificDto.from(reservation));
    }

    @Operation(summary = "전체 예약 조회", description = "모든 예약 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = ReservationSpecificDto.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/all")
    public ResponseEntity<List<ReservationSpecificDto>> getAllReservations() {
        List<Reservation> reservations = reservationService.allReservations();
        List<ReservationSpecificDto> reservationSpecificDtos = reservations.stream()
                .map(ReservationSpecificDto::from)
                .toList();
        return ResponseEntity.ok(reservationSpecificDtos);
    }
}
