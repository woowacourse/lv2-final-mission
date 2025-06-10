package finalmission.application.dto.request;

import finalmission.domain.MonsterEnergy;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreateReservationRequest(
        @NotNull
        MonsterEnergy monsterEnergy,
        @NotNull
        int quantity,
        @NotNull
        LocalDateTime dateTime
) {
}
