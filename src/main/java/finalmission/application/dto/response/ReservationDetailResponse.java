package finalmission.application.dto.response;

import finalmission.domain.MonsterEnergy;
import java.time.LocalDateTime;

public record ReservationDetailResponse(
        Long id,
        MonsterEnergy monsterEnergy,
        int quantity,
        LocalDateTime dateTime
) {
}
