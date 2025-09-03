package finalmission.movie.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record MovieReservationReadResponse(
        Long id,
        String movieName,
        LocalDate date,
        LocalTime startAt,
        Integer seat
) {
}
