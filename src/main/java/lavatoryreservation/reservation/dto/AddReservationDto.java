package lavatoryreservation.reservation.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "예약 생성 요청 DTO")
public record AddReservationDto(
        @Schema(description = "회원 ID", example = "1", required = true)
        Long memberId,
        
        @Schema(description = "화장실 ID", example = "1", required = true)
        Long toiletId,
        
        @Schema(description = "예약 시작 시간", example = "2024-01-15T09:00:00", required = true)
        LocalDateTime startTime,
        
        @Schema(description = "예약 종료 시간", example = "2024-01-15T10:00:00", required = true)
        LocalDateTime endTime
) {

}
