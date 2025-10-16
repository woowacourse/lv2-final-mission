package shh.reservation.domain.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shh.reservation.domain.Reservation;
import shh.reservation.domain.ReservationTime;
import shh.stall.domain.Stall;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByMemberId(Long memberId);

    List<Reservation> findAllByStallId(Long stallId);

    boolean existsByDateAndTimeAndStall(LocalDate date, ReservationTime time, Stall stall);
}
