package finalmission.reservation.ui.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record CreateReservationTimeRequest(
        @JsonFormat(pattern = "HH:mm")
        @NotNull
        LocalTime startAt
) {

}
