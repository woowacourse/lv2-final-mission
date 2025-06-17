package finalmission.reservation.repository;

import finalmission.reservation.entity.Reservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByMemberId(Long memberId);
}
