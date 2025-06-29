package finalmission.reservation.infrastructure;

import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationSlot;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByReservationSlot(ReservationSlot reservationSlot);

    Optional<Reservation> findByReservationSlot(ReservationSlot reservationSlot);

    List<Reservation> findAllByMember(Member member);
}
