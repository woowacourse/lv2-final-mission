package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Table(name = "reservation")
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Sport sport;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ReservationTime time;

    private Reservation(final Long id, final LocalDate date, final Member member, final Sport sport,
            final ReservationTime time) {
        this.id = id;
        this.date = date;
        this.member = member;
        this.sport = sport;
        this.time = time;
    }

    protected Reservation() {
    }

    public static Reservation withoutId(final LocalDate date, final Member member, final Sport sport,
            final ReservationTime time) {
        return new Reservation(null, date, member, sport, time);
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Member getMember() {
        return member;
    }

    public Sport getSport() {
        return sport;
    }

    public ReservationTime getTime() {
        return time;
    }
}
