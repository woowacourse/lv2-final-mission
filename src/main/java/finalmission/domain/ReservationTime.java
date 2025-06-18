package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalTime;

@Entity
public class ReservationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime starAt;

    public ReservationTime() {
    }

    public ReservationTime(Long id, LocalTime starAt) {
        this.id = id;
        this.starAt = starAt;
    }
}
