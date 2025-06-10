package finalmission.domain;

import java.time.LocalDate;

public record Booking(
    Member member,
    Gym gym,
    LocalDate date
) {

}
