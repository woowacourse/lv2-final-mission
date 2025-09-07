package lavatoryreservation.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "예약 삭제 요청 DTO")
public record DeleteReservationDto(
        @Schema(description = "예약 ID", example = "1", required = true)
        Long reservationId
) {

}
