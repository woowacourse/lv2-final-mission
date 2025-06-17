package finalmission.domain.schedule.detail;

import finalmission.domain.reservation.Reservation;
import finalmission.domain.reservation.detail.NumberOfGuest;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.util.List;
import java.util.Objects;

@Embeddable
public class ScheduleDetail {

    @Embedded
    private DateSchedule date;

    @Embedded
    private TimeSchedule time;

    @Embedded
    private MaximumCapacity maximumCapacity;

    protected ScheduleDetail() {
    }

    public int calculateRemainingCapacity(final List<Reservation> reservations) {
        final int numberOfReserved = reservations.stream()
                .mapToInt(reservation -> reservation.getNumberOfGuest().getValue())
                .sum();
        validateNumberOfReserved(numberOfReserved);
        return getMaximumCapacity().getValue() - numberOfReserved;
    }

    public void validateNewReservationNumberOfGuest(final List<Reservation> reservations,
                                                    final NumberOfGuest numberOfGuest) {
        if (calculateRemainingCapacity(reservations) < numberOfGuest.getValue()) {
            throw new IllegalArgumentException("[400] 예약 가능 인원을 초과했습니다.");
        }
    }

    private void validateNumberOfReserved(final int numberOfReserved) {
        if (numberOfReserved > getMaximumCapacity().getValue()) {
            throw new IllegalStateException("[500] 예약 상태 오류");
        }
    }

    public DateSchedule getDate() {
        return date;
    }

    public TimeSchedule getTime() {
        return time;
    }

    public MaximumCapacity getMaximumCapacity() {
        return maximumCapacity;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final ScheduleDetail that)) {
            return false;
        }

        return Objects.equals(getDate(), that.getDate()) && Objects.equals(getTime(), that.getTime())
                && Objects.equals(getMaximumCapacity(), that.getMaximumCapacity());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getDate());
        result = 31 * result + Objects.hashCode(getTime());
        result = 31 * result + Objects.hashCode(getMaximumCapacity());
        return result;
    }
}
