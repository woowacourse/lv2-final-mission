package finalmission.reservation.domain;

import java.util.List;

public interface ReservationSlotRepository {

    ReservationSlot save(ReservationSlot reservationSlot);

    void deleteById(Long reservationSlotId);

    boolean existsByRestaurantId(Long restaurantId);

    ReservationSlot getByRestaurantId(Long restaurantId);

    List<ReservationSlot> findAll();
}
