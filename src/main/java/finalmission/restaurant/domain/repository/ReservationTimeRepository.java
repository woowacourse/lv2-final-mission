package finalmission.restaurant.domain.repository;

import finalmission.restaurant.domain.ReservationTime;
import java.time.LocalTime;
import java.util.List;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    void deleteById(Long timeId);

    ReservationTime getById(Long timeId);

    List<ReservationTime> findAllByStartAt(LocalTime startAt);

    List<ReservationTime> findAll();
}
