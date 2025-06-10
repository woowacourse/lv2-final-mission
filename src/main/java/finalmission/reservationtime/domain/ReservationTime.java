package finalmission.reservationtime.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalTime;

@Entity
@Getter
public class ReservationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime startAt;

    protected ReservationTime() {

    }

    public ReservationTime(LocalTime startAt) {
        this.id = null;
        this.startAt = startAt;
    }
}
