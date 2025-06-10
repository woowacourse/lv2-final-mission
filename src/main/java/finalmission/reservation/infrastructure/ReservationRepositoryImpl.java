package finalmission.reservation.infrastructure;

import finalmission.exception.resource.ResourceNotFoundException;
import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.domain.ReservationSlot;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JpaReservationRepository jpaReservationRepository;

    @Override
    public Reservation save(final Reservation reservation) {
        return jpaReservationRepository.save(reservation);
    }

    @Override
    public void deleteById(final Long id) {
        jpaReservationRepository.deleteById(id);
    }

    public boolean existsByReservationSlot(
            final ReservationSlot reservationSlot
    ) {
        return jpaReservationRepository.existsByReservationSlot(reservationSlot);
    }

    @Override
    public Reservation getById(final Long reservationId) {
        return jpaReservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 예약을 찾을 수 없습니다. id = " + reservationId));
    }

    @Override
    public Optional<Reservation> findByReservationSlot(final ReservationSlot reservationSlot) {
        return jpaReservationRepository.findByReservationSlot(reservationSlot);
    }

    @Override
    public List<Reservation> findAll() {
        return jpaReservationRepository.findAll();
    }

    @Override
    public List<Reservation> findAllByMember(final Member member) {
        return jpaReservationRepository.findAllByMember(member);
    }
}
