package finalmission.waiting.ui.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateWaitingRequest(
        @NotNull(message = "date는 필수입니다.")
        LocalDate date,
        @NotNull(message = "timeId는 필수입니다.")
        Long timeId,
        @NotNull(message = "restaurantId는 필수입니다.")
        Long restaurantId,
        @NotNull(message = "memberId는 필수입니다.")
        Long memberId
) {

}
