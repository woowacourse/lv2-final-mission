package finalmission.umbrella.repository;

import finalmission.umbrella.domain.Umbrella;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UmbrellaRepository extends JpaRepository<Umbrella, Long> {

    @Query("SELECT u " +
            " FROM Umbrella u" +
            " WHERE u " +
            " NOT IN (SELECT r.umbrella FROM Reservation r WHERE r.reservationDate = :reservation_date)")
    List<Umbrella> findAvailableUmbrella(@Param("reservation_date") LocalDate reservationDate);
}
