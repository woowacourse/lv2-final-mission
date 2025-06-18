package finalmission.waiting.domain;

import finalmission.member.domain.Member;
import finalmission.reservation.domain.ReservationSlot;
import java.util.List;

public interface WaitingRepository {

    Waiting save(Waiting waiting);

    void delete(Waiting waiting);

    void deleteById(Long waitingId);

    boolean existsById(Long waitingId);

    boolean existsByReservationSlotAndMember(ReservationSlot reservationSlot, Member member);

    Waiting getById(Long waitingId);

    List<Waiting> findAllWaitingByMemberId(Long memberId);
    
    List<Waiting> findAllWaitingByReservationSlotId(Long reservationSlotId);

    List<Waiting> findAll();
}
