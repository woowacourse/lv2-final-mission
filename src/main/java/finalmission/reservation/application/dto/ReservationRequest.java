package finalmission.reservation.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        @NotNull(message = "회의실은 필수 입력값입니다.") Long roomId,
        @NotNull(message = "날짜는 필수 입력값입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate date,
        @NotNull(message = "시작 시간은 필수 입력값입니다.")
        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt,
        @NotNull(message = "끝 시간은 필수 입력값입니다.")
        @JsonFormat(pattern = "HH:mm")
        LocalTime endAt) {
}
