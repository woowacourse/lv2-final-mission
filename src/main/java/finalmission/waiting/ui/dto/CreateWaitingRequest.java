package finalmission.waiting.ui.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateWaitingRequest(
        @NotNull
        LocalDate date,
        @NotNull
        Long timeId,
        @NotNull
        Long restaurantId,
        @NotNull
        Long memberId
) {

}
