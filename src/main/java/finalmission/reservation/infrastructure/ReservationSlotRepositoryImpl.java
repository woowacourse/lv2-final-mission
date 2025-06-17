package finalmission.reservation.infrastructure;

import finalmission.reservation.domain.ReservationSlot;
import finalmission.reservation.domain.ReservationSlotRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationSlotRepositoryImpl implements ReservationSlotRepository {

    private final JpaReservationSlotRepository jpaReservationSlotRepository;

    @Override
    public ReservationSlot save(final ReservationSlot reservationSlot) {
        return jpaReservationSlotRepository.save(reservationSlot);
    }

    @Override
    public void deleteById(final Long id) {
        jpaReservationSlotRepository.deleteById(id);
    }

    @Override
    public List<ReservationSlot> findAll() {
        return jpaReservationSlotRepository.findAll();
    }
}
