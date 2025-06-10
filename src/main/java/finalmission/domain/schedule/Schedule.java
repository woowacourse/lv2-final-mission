package finalmission.domain.schedule;

import finalmission.domain.reservation.Reservation;
import finalmission.domain.restaurant.Restaurant;
import finalmission.domain.schedule.detail.DateSchedule;
import finalmission.domain.schedule.detail.ScheduleDetail;
import finalmission.domain.schedule.detail.TimeSchedule;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.List;
import java.util.Objects;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ScheduleDetail detail;

    @ManyToOne
    private Restaurant restaurant;

    public Schedule() {
    }

    public int getRemainingCapacity(final List<Reservation> reservations) {
        final int numberOfReserved = reservations.stream()
                .mapToInt(reservation -> reservation.getNumberOfGuest().getValue()).sum();
        if (numberOfReserved > detail.getMaximumCapacity().getValue()) {
            throw new IllegalStateException("[500] 예약 상태 오류");
        }
        return detail.getMaximumCapacity().getValue() - numberOfReserved;
    }

    public Long getId() {
        return id;
    }

    public DateSchedule getDate() {
        return detail.getDate();
    }

    public TimeSchedule getTime() {
        return detail.getTime();
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final Schedule schedule)) {
            return false;
        }

        return Objects.equals(getId(), schedule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
