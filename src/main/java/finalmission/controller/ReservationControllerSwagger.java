package finalmission.controller;

import finalmission.controller.dto.ReservationDetailResponse;
import finalmission.controller.dto.ReservationRequest;
import finalmission.controller.dto.ReservationResponse;
import finalmission.controller.dto.ReservationResponses;
import finalmission.domain.Member;
import finalmission.global.config.AuthenticationPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "회의실 예약", description = "회의실 예약 API")
public interface ReservationControllerSwagger {

    @Operation(summary = "예약 전체 조회", description = "예약 전체 조회해 정보 반환 API")
    ReservationResponses getAllReservation();

    @Operation(summary = "예약 상세 조회", description = "한 예약의 상세 조회해 정보 반환 API")
    ReservationDetailResponse getDetailReservation(@PathVariable Long reservationId,
                                                   @AuthenticationPrincipal Member member);

    @Operation(summary = "예약 등록", description = "예약 등록후 정보 반환 API")
    ReservationResponse registerReservation(@AuthenticationPrincipal Member member,
                                            @RequestBody ReservationRequest request);
}
