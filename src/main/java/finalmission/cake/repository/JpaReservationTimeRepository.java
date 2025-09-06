package finalmission.cake.repository;

import finalmission.cake.model.ReservationTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservationTimeRepository extends ReservationTimeRepository, JpaRepository<ReservationTime, Long> {
}
