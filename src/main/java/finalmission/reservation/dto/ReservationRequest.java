package finalmission.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        @NotBlank(message = "예약자 이름이 없습니다.")
        String name,
        @NotNull(message = "회의실 식별자가 없습니다.")
        Long roomId,
        @NotNull(message = "예약 날짜가 없습니다.")
        LocalDate date,
        @NotNull(message = "예약 시간이 없습니다.")
        LocalTime time
) {
}
