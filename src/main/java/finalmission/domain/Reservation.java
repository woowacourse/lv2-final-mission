package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime startAt;

    private LocalTime endAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Toilet toilet;

    protected Reservation() {
    }

    public Reservation(Long id, LocalDate date, LocalTime startAt, LocalTime endAt, Member member, Toilet toilet) {
        this.id = id;
        this.date = date;
        this.startAt = startAt;
        this.endAt = endAt;
        this.member = member;
        this.toilet = toilet;
    }

    public Reservation(LocalDate date, LocalTime startAt, LocalTime endAt, Member member, Toilet toilet) {
        this(null, date, startAt, endAt, member, toilet);
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public LocalTime getEndAt() {
        return endAt;
    }

    public Member getMember() {
        return member;
    }

    public Toilet getToilet() {
        return toilet;
    }
}
