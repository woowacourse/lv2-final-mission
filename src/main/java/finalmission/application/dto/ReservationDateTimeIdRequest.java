package finalmission.application.dto;

import jakarta.validation.constraints.NotNull;

public class ReservationDateTimeIdRequest {
    @NotNull(message = "값을 반드시 입력해야 합니다")
    private final Long coachId;

    public ReservationDateTimeIdRequest(final Long coachId) {
        this.coachId = coachId;
    }

    public Long getCoachId() {
        return coachId;
    }
}
