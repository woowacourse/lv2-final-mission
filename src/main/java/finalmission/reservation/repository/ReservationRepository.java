package finalmission.reservation.repository;

import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("""
    SELECT r
    FROM Reservation r
        JOIN FETCH r.reservationInformation
    WHERE r.customer.id = :memberId
    """)
    List<Reservation> findAllByMemberId(@Param("memberId") Long memberId);
    @Query("""
    SELECT r
    FROM Reservation r
        JOIN FETCH r.reservationInformation
        JOIN FETCH r.reservationInformation.restaurant
    WHERE r.id = :id
    """)
    Optional<Reservation> findWithDetailsById(@Param("id") Long id);
    boolean existsByCustomerAndId(Member member, Long id);
}
