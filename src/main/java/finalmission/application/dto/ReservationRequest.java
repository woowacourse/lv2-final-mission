package finalmission.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ReservationRequest {
    @NotNull(message = "값을 반드시 입력해야 합니다")
    private final Long crewId;

    @NotBlank(message = "값을 반드시 입력해야 합니다")
    private final String coach;

    @NotNull(message = "값을 반드시 입력해야 합니다")
    private final LocalDateTime localDateTime;

    public ReservationRequest(final Long crewId, final String coach, final LocalDateTime localDateTime) {
        this.crewId = crewId;
        this.coach = coach;
        this.localDateTime = localDateTime;
    }

    public Long getCrewId() {
        return crewId;
    }

    public String getCoach() {
        return coach;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
