package finalmission.dto;

import finalmission.domain.Rent;
import java.time.LocalDate;
import java.time.LocalTime;

public record ResponseRent(
        ResponseCar car,
        LocalDate date,
        LocalTime startTime,
        LocalTime returnTime
) {

    public static ResponseRent from(Rent rent) {
        return new ResponseRent(
                ResponseCar.from(rent.getCar()),
                rent.getDate(),
                rent.getStartTime(),
                rent.getReturnTime()
        );
    }
}
