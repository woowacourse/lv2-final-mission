package finalmission.reservation.domain;

import finalmission.member.domain.Member;
import finalmission.time.domain.ReservationTime;
import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private final Long id;
    private final LocalDate date;
    private final ReservationTime time;
    private final Member member;

    public Reservation(Member member, LocalDate date, ReservationTime time) {
        this.id = null;
        this.date = Objects.requireNonNull(date);
        this.time = Objects.requireNonNull(time);
        this.member = Objects.requireNonNull(member);
    }

    public Reservation(Long id, Member member, LocalDate date, ReservationTime time) {
        this.id = Objects.requireNonNull(id);
        this.date = Objects.requireNonNull(date);
        this.time = Objects.requireNonNull(time);
        this.member = Objects.requireNonNull(member);
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Reservation that = (Reservation) other;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
