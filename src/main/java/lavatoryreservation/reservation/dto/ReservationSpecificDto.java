package lavatoryreservation.reservation.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lavatoryreservation.lavatory.domain.Lavatory;
import lavatoryreservation.reservation.domain.Reservation;
import lavatoryreservation.reservation.domain.ToiletTime;
import lavatoryreservation.toilet.domain.Toilet;

@Schema(description = "예약 상세 정보 응답 DTO")
public record ReservationSpecificDto(
        @Schema(description = "예약자 이름", example = "홍길동")
        String reserverName,
        
        @Schema(description = "화장실실 이름", example = "메인 화장실실")
        String toiletName,
        
        @Schema(description = "화장실 설명", example = "1층 남자 화장실")
        String toiletDescription,
        
        @Schema(description = "예약 시작 시간", example = "2024-01-15T09:00:00")
        LocalDateTime startTime,
        
        @Schema(description = "예약 종료 시간", example = "2024-01-15T10:00:00")
        LocalDateTime endTime
) {
    public static ReservationSpecificDto from(Reservation reservation) {

        Toilet toilet = reservation.getToilet();
        ToiletTime toiletTime = reservation.getToiletTime();
        LocalDateTime toiletTimeStartTime = toiletTime.getStartTime();
        LocalDateTime toiletTimeEndTime = toiletTime.getEndTime();
        String toiletDescription = toilet.getDescription();
        Lavatory lavatory = toilet.getLavatory();
        String lavatoryDescription = lavatory.getDescription();
        return new ReservationSpecificDto(reservation.getAlias(),
                lavatoryDescription,
                toiletDescription,
                toiletTimeStartTime,
                toiletTimeEndTime);
    }
}
