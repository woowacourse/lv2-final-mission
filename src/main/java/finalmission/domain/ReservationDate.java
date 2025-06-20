package finalmission.domain;

import finalmission.exception.BadRequestException;
import finalmission.exception.ErrorCode;
import jakarta.persistence.Embeddable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationDate {

    private LocalDate reservationDate;

    public ReservationDate(final LocalDate reservationDate) {
        validateWeekDay(reservationDate);
        this.reservationDate = reservationDate;
    }

    private void validateWeekDay(final LocalDate reservationDate) {
        if (reservationDate.getDayOfWeek() == DayOfWeek.SUNDAY || reservationDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            throw new BadRequestException(ErrorCode.HOLIDAY_RESERVATION_NOT_ALLOWED);
        }
    }

    public boolean isEqual(final ReservationDate other) {
        return this.reservationDate.isEqual(other.reservationDate);
    }
}
