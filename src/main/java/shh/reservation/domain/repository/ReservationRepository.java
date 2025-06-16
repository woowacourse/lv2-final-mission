package shh.reservation.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shh.reservation.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByMemberId(Long memberId);
}
