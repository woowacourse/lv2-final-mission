package finalmission.waiting.infrastructure;

import finalmission.member.domain.Member;
import finalmission.reservation.domain.ReservationSlot;
import finalmission.waiting.domain.Waiting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaWaitingRepository extends JpaRepository<Waiting, Long> {

    boolean existsByReservationSlotAndMember(ReservationSlot reservationSlot, Member member);

    List<Waiting> findAllWaitingByMemberId(Long memberId);

    List<Waiting> findAllWaitingByReservationSlotId(Long reservationSlotId);
}
