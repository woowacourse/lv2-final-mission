package finalmission.repository;

import finalmission.entity.MovieReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieReservationRepository extends JpaRepository<MovieReservation, Long> {
}
