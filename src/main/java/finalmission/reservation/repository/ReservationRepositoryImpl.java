package finalmission.reservation.repository;

import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JpaReservationRepository jpaReservationRepository;

    public ReservationRepositoryImpl(final JpaReservationRepository jpaReservationRepository) {
        this.jpaReservationRepository = jpaReservationRepository;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return jpaReservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> findAll() {
        return jpaReservationRepository.findAll();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return jpaReservationRepository.findById(id);
    }

    @Override
    public void deleteById(Long reservationId) {
        jpaReservationRepository.deleteById(reservationId);
    }

    @Override
    public List<Reservation> findByMemberId(Long memberId) {
        return jpaReservationRepository.findByMemberId(memberId);
    }
}
