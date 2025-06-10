package finalmission.api.v1.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.api.v1.restaurant.domain.ReservationCapacity;
import java.time.LocalTime;

public record ReservationCapacityResponse(Long id, @JsonFormat(pattern = "HH:mm") LocalTime reservationTime, Long numberOfPeople) {
    public ReservationCapacityResponse(final ReservationCapacity reservationCapacity) {
        this(
                reservationCapacity.getId(),
                reservationCapacity.getReservationTime().getTime(),
                reservationCapacity.getNumberOfPeople()
        );
    }
}
