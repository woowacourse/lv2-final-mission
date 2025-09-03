package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalTime;

@Table(name = "reservation_time")
@Entity
public class ReservationTime {
    @Column(nullable = false, unique = true)
    LocalTime time;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ReservationTime(final Long id, final LocalTime time) {
        this.id = id;
        this.time = time;
    }

    protected ReservationTime() {
    }

    public static ReservationTime withoutId(final LocalTime time) {
        return new ReservationTime(null, time);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
