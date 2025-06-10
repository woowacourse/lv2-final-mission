package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Coach coach;

    @ManyToOne
    private Crew crew;

    @ManyToOne
    private ReservationDateTime reservationDateTime;

    protected Reservation() {
    }

    public Long getId() {
        return id;
    }

    public Coach getCoach() {
        return coach;
    }

    public Crew getCrew() {
        return crew;
    }

    public ReservationDateTime getReservationDateTime() {
        return reservationDateTime;
    }
}
