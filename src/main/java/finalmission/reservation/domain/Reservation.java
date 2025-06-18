package finalmission.reservation.domain;

import finalmission.common.exception.InvalidInputException;
import finalmission.concert.domain.Concert;
import finalmission.member.domain.Member;
import finalmission.seat.domain.Seat;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Concert concert;

    @ManyToOne(fetch = FetchType.LAZY)
    private Seat seat;

    public Reservation(final Member member, final Concert concert, final Seat seat) {
        validate(member, concert, seat);
        this.member = member;
        this.concert = concert;
        this.seat = seat;
    }

    public void changeSeat(final Seat seat) {
        if (seat == null) {
            throw new InvalidInputException("좌석은 null일 수 없습니다.");
        }
        this.seat = seat;
    }

    private void validate(final Member member, final Concert concert, final Seat seat) {
        if (member == null) {
            throw new InvalidInputException("예약자는 null일 수 없습니다.");
        }
        if (concert == null) {
            throw new InvalidInputException("공연은 null일 수 없습니다.");
        }
        if (seat == null) {
            throw new InvalidInputException("좌석은 null일 수 없습니다.");
        }
    }
}
