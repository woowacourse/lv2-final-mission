package finalmission.reservation.domain;

import java.util.List;

public interface ReservationSlotRepository {

    ReservationSlot save(ReservationSlot reservationSlot);

    void deleteById(Long id);

    List<ReservationSlot> findAll();
}
