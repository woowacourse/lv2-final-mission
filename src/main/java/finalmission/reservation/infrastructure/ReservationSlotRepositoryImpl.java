package finalmission.reservation.infrastructure;

import finalmission.exception.resource.ResourceNotFoundException;
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
    public void deleteById(final Long reservationSlotId) {
        jpaReservationSlotRepository.deleteById(reservationSlotId);
    }

    @Override
    public boolean existsByRestaurantId(final Long restaurantId) {
        return jpaReservationSlotRepository.existsByRestaurantId(restaurantId);
    }

    @Override
    public ReservationSlot getByRestaurantId(final Long restaurantId) {
        return jpaReservationSlotRepository.findByRestaurantId(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 예약 슬롯이 존재하지 않습니다. id = " + restaurantId));
    }

    @Override
    public List<ReservationSlot> findAll() {
        return jpaReservationSlotRepository.findAll();
    }
}
