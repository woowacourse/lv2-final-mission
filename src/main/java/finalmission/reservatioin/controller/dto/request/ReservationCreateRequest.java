package finalmission.reservatioin.controller.dto.request;

import finalmission.reservatioin.entity.ReservationTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ReservationCreateRequest(
        @NotNull
        @NotBlank
        String customerName,

        @NotNull
        @NotBlank
        String omakaseStoreName,

        @NotNull
        LocalDate reservationDate,

        @NotNull
        ReservationTime reservationTime
) {
}
