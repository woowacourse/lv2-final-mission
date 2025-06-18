package finalmission.movie.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record MovieSlotCreateRequest(Long movieId, LocalDate date, LocalTime startAt, Integer seats) {

}
