package finalmission.domain;

import finalmission.global.BaseTimeEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CanceledReservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private ReservationDate reservationDate;

    @Embedded
    private ReservationTimeSlot reservationTimeSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int numberOfPeople;

    public static CanceledReservation from(final Reservation reservation) {
        return new CanceledReservation(
                reservation.getReservationDate(),
                reservation.getReservationTimeSlot(),
                reservation.getMember(),
                reservation.getNumberOfPeople()
        );
    }

    private CanceledReservation(final ReservationDate reservationDate, final ReservationTimeSlot reservationTimeSlot,
                               final Member member,
                               final int numberOfPeople) {
        this.reservationDate = reservationDate;
        this.reservationTimeSlot = reservationTimeSlot;
        this.member = member;
        this.numberOfPeople = numberOfPeople;
    }
}
