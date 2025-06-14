package finalmission.reservation.repository;

import finalmission.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("""
    SELECT r
    FROM Reservation r
    JOIN FETCH ReservationInformation info
    ON r.reservationInformation.id = info.id
    WHERE r.customer.id = :memberId
    """)
    List<Reservation> findAllByMemberId(Long memberId);
}
