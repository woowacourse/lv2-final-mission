package finalmission.reservation.ui.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.reservation.domain.ReservationTime;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record ReservationTimeResponse(
        Long id,
        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt
) {

    public static ReservationTimeResponse from(final ReservationTime reservationTime) {
        return ReservationTimeResponse.builder()
                .id(reservationTime.getId())
                .startAt(reservationTime.getStartAt())
                .build();
    }
}
