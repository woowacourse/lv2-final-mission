package finalmission.movie.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record MovieSlotReadResponse(
        Long id,
        String movieName,
        LocalDate date,
        LocalTime startAt,
        Integer price,
        Integer totalSeats,
        Integer leftSeats
) {
    private static final Integer BASE_PRICE = 10000;

    public MovieSlotReadResponse(
            Long id,
            String movieName,
            LocalDate date,
            LocalTime startAt,
            boolean isHoliday,
            Integer totalSeats,
            Integer leftSeats
    ) {
        this(id, movieName, date, startAt, calculatePrice(isHoliday), totalSeats, leftSeats);
    }

    private static Integer calculatePrice(boolean isHoliday) {
        if (isHoliday) {
            return (int) (BASE_PRICE * 1.5);
        }
        return BASE_PRICE;
    }
}
