package finalmission.waiting.infrastructure;

import finalmission.exception.resource.ResourceNotFoundException;
import finalmission.member.domain.Member;
import finalmission.reservation.domain.ReservationSlot;
import finalmission.waiting.domain.Waiting;
import finalmission.waiting.domain.WaitingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WaitingRepositoryImpl implements WaitingRepository {

    private final JpaWaitingRepository jpaWaitingRepository;

    @Override
    public Waiting save(final Waiting waiting) {
        return jpaWaitingRepository.save(waiting);
    }

    @Override
    public void delete(final Waiting waiting) {
        jpaWaitingRepository.delete(waiting);
    }

    @Override
    public void deleteById(final Long waitingId) {
        jpaWaitingRepository.deleteById(waitingId);
    }

    @Override
    public boolean existsById(final Long waitingId) {
        return jpaWaitingRepository.existsById(waitingId);
    }

    @Override
    public boolean existsByReservationSlotAndMember(final ReservationSlot reservationSlot, final Member member) {
        return jpaWaitingRepository.existsByReservationSlotAndMember(reservationSlot, member);
    }

    @Override
    public Waiting getById(final Long waitingId) {
        return jpaWaitingRepository.findById(waitingId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 예약 대기를 찾을 수 없습니다. id = " + waitingId));
    }

    @Override
    public List<Waiting> findAllWaitingByMemberId(final Long memberId) {
        return jpaWaitingRepository.findAllWaitingByMemberId(memberId);
    }

    @Override
    public List<Waiting> findAllWaitingByReservationSlotId(final Long reservationSlotId) {
        return jpaWaitingRepository.findAllWaitingByReservationSlotId(reservationSlotId);
    }

    @Override
    public List<Waiting> findAll() {
        return jpaWaitingRepository.findAll();
    }
}
