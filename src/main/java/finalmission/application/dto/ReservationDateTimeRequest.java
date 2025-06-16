package finalmission.application.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ReservationDateTimeRequest {
    @NotNull(message = "값을 반드시 입력해야 합니다")
    private final Long coachId;

    @NotNull(message = "값을 반드시 입력해야 합니다")
    private final LocalDateTime dateTime;

    public ReservationDateTimeRequest(final Long coachId, final LocalDateTime dateTime) {
        this.coachId = coachId;
        this.dateTime = dateTime;
    }

    public Long getCoachId() {
        return coachId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
