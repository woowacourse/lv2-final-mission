package finalmission.reservation.repository;

import finalmission.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByMemberId(long id);
}
