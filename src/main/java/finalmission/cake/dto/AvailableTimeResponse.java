package finalmission.cake.dto;

import finalmission.cake.model.ReservationTime;
import java.time.LocalTime;

public record AvailableTimeResponse(
        Long id,
        LocalTime time,
        boolean isAvailable
) {

    public static AvailableTimeResponse from(ReservationTime time, boolean isAvailable) {
        return new AvailableTimeResponse(time.getId(), time.getTime(), isAvailable);
    }
}
