package finalmission.mungPlan.ui.dto;

import java.time.LocalTime;

public record CreateReservationRequest(
        Long planDateId,
        LocalTime planTimeStartAt,
        String userId
) {
}
