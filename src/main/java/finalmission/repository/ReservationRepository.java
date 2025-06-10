package finalmission.repository;

import finalmission.domain.entity.Reservation;
import finalmission.domain.entity.Trainer;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByTrainerAndDateAfterOrderByDateAsc(Trainer trainer, LocalDate localDate);
}
