package finalmission.reservationTime.domain;

import jakarta.persistence.*;

@Entity
public class ReservationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booked_at")
    private String bookedAt;

    protected ReservationTime(){}

    private ReservationTime(final String bookedAt) {
        this.bookedAt = bookedAt;
    }

    public static ReservationTime beforeSave(final String bookedAt) {
        return new ReservationTime(bookedAt);
    }

    public Long getId() {
        return id;
    }

    public String getBookedAt() {
        return bookedAt;
    }
}
