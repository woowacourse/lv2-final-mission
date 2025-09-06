package finalmission.cake.repository;

import finalmission.cake.model.ReservationTime;
import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {
    List<ReservationTime> findAll();

    Optional<ReservationTime> findById(Long id);
}
