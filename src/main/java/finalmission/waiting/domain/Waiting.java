package finalmission.waiting.domain;

import finalmission.member.domain.Member;
import finalmission.reservation.domain.ReservationSlot;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "waitings")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = {"id"})
public class Waiting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "reservation_slot_id", nullable = false)
    private ReservationSlot reservationSlot;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Waiting(
            final ReservationSlot reservationSlot,
            final Member member
    ) {
        validateReservationSlot(reservationSlot);
        validateMember(member);

        this.reservationSlot = reservationSlot;
        this.member = member;
    }

    private void validateReservationSlot(final ReservationSlot reservationSlot) {
        if (reservationSlot == null) {
            throw new IllegalArgumentException("예약 슬롯은 null이면 안됩니다.");
        }
    }

    private void validateMember(final Member member) {
        if (member == null) {
            throw new IllegalArgumentException("멤버는 null이면 안됩니다.");
        }
    }
}
