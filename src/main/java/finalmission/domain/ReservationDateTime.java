package finalmission.domain;

import finalmission.exception.InvalidReservationTimeException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;

@Entity
@Getter
public class ReservationDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startAt;

    public ReservationDateTime(final Long id, final LocalDate date, final LocalTime startAt) {
        validateTime(startAt);
        this.id = id;
        this.date = date;
        this.startAt = startAt;
    }

    public ReservationDateTime() {
    }

    public static ReservationDateTime createWithoutId(LocalDate date, LocalTime startAt) {
        return new ReservationDateTime(null, date, startAt);
    }

    private void validateTime(LocalTime time) {
        if (time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(23, 0))) {
            throw new InvalidReservationTimeException();
        }
    }
}
