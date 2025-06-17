package finalmission.dto;

import finalmission.domain.Rent;
import java.time.LocalDate;
import java.time.LocalTime;

public record ResponseRentDetail(
        Long rentId,
        ResponseCar car,
        LocalDate date,
        LocalTime startTime,
        LocalTime returnTime,
        Long fee
) {

    public static ResponseRentDetail from(Rent rent) {
        return new ResponseRentDetail(
                rent.getId(),
                ResponseCar.from(rent.getCar()),
                rent.getDate(),
                rent.getStartTime(),
                rent.getReturnTime(),
                rent.getFee()
        );
    }
}
