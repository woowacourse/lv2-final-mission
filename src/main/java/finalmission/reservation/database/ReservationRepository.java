package finalmission.reservation.database;

import finalmission.member.model.Member;
import finalmission.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
        SELECT r
        FROM Reservation r
            JOIN FETCH r.time t
        WHERE :startDate <= r.date AND r.date <= :endDate
    """)
    List<Reservation> findReservationOfPeriod(LocalDate startDate, LocalDate endDate);

    List<Reservation> findByMember(Member member);

    boolean existsByDateAndTimeId(LocalDate date, Long timeId);
}
