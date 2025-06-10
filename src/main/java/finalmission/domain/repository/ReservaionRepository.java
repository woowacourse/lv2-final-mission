package finalmission.domain.repository;

import finalmission.domain.Reservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaionRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUserId(Long id);
}
