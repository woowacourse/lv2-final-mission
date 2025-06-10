package finalmission.presentation.controller;

import finalmission.dto.ReservationDetailResponseDto;
import finalmission.dto.ReservationRegisterDto;
import finalmission.dto.ReservationResponseDto;
import finalmission.dto.ReservationUpdateDto;
import finalmission.model.Member;
import finalmission.presentation.service.ReservationService;
import finalmission.service.AuthenticatedMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "예약 관련 API")
@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "예약 등록")
    @PostMapping
    public void registerReservation(@RequestBody ReservationRegisterDto reservationRegisterDto,
                                    @AuthenticatedMember Member member) {
        reservationService.registerReservation(reservationRegisterDto, member);
    }

    @Operation(summary = "회원의 예약 조회")
    @GetMapping("/mine")
    public List<ReservationResponseDto> getMyReservations(@AuthenticatedMember Member member) {
        return reservationService.getMyReservations(member);
    }

    @Operation(summary = "회원의 예약 상세 조회")
    @GetMapping("/{reservationId}")
    public ReservationDetailResponseDto getReservationInDetail(@PathVariable Long reservationId,
                                                               @AuthenticatedMember Member member) {
        return reservationService.getReservation(reservationId, member);
    }

    @Operation(summary = "회원의 예약 수정")
    @PutMapping("/{reservationId}")
    public void updateReservation(@PathVariable Long reservationId,
                                  @RequestBody ReservationUpdateDto reservationUpdateDto,
                                  @AuthenticatedMember Member member) {
        reservationService.updateReservation(reservationId, reservationUpdateDto, member);
    }

    @Operation(summary = "회원의 예약 삭제")
    @DeleteMapping("/{reservationId}")
    public void deleteReservation(@PathVariable Long reservationId, @AuthenticatedMember Member member) {
        reservationService.deleteReservation(reservationId, member);
    }
}
