package finalmission.ballparkreservation.reservation;

import finalmission.ballparkreservation.member.Member;
import finalmission.ballparkreservation.seat.Seat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    private Seat seat;

    private LocalDate date;

    public Reservation(final Member member, final Seat seat, final LocalDate date) {
        this.member = member;
        this.seat = seat;
        this.date = date;
    }
}
