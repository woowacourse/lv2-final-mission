package finalmission.domain;

import finalmission.exception.BadRequestException;
import finalmission.exception.ErrorCode;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseTimeEntity {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    private int numberOfPeople;

    public Reservation(final ReservationDate reservationDate, final ReservationTimeSlot reservationTimeSlot,
                       final Member member,
                       final int numberOfPeople) {
        this.reservationDate = reservationDate;
        this.reservationTimeSlot = reservationTimeSlot;
        this.member = member;
        this.numberOfPeople = numberOfPeople;
    }

    public void validateDuplicate(final Reservation other) {
        if (reservationDate.isEqual(other.reservationDate) && reservationTimeSlot.isInTime(other.reservationTimeSlot)) {
            throw new BadRequestException(ErrorCode.DUPLICATE_RESERVATION_TIME);
        }
    }

    public void updateReservationDate(final ReservationDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void updateReservationTimeSlot(final ReservationTimeSlot reservationTimeSlot) {
        this.reservationTimeSlot = reservationTimeSlot;
    }

    public void updateNumberOfPeople(final int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public void updateMember(final Member member) {
        this.member = member;
    }
}
