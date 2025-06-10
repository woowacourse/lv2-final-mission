package finalmission.waiting.infrastructure;

import finalmission.member.domain.Member;
import finalmission.reservation.domain.ReservationSlot;
import finalmission.waiting.domain.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaWaitingRepository extends JpaRepository<Waiting, Long> {

    boolean existsByReservationSlotAndMember(ReservationSlot reservationSlot, Member member);
}
