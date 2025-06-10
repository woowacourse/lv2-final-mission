package finalmission.infrastructure;

import finalmission.domain.Reservation;
import finalmission.domain.ReservationRepository;
import finalmission.infrastructure.jpa.ReservationJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public Reservation save(final Reservation reservation) {
        return reservationJpaRepository.save(reservation);
    }

    @Override
    public void deleteById(final long id) {
        reservationJpaRepository.deleteById(id);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationJpaRepository.findAll();
    }

    @Override
    public List<Reservation> findAllByMemberId(final long memberId) {
        return reservationJpaRepository.findAllByMemberId(memberId);
    }
}
