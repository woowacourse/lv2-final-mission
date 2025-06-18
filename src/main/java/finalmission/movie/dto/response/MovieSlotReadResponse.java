package finalmission.movie.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record MovieSlotReadResponse(Long id,
                                    String movieName,
                                    LocalDate date,
                                    LocalTime startAt,
                                    Integer totalSeats,
                                    Integer leftSeats) {
}
