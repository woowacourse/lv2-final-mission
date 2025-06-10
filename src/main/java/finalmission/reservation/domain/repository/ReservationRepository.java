package finalmission.reservation.domain.repository;

import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMember(Member member);

    @Query("""
            SELECT r FROM Reservation r
            JOIN FETCH r.member
            JOIN FETCH r.meetingRoom
            WHERE (:roomId IS NULL OR r.meetingRoom.id = :roomId)
            """)
    List<Reservation> findFiltered(@Param("roomId") Long roomId);
}
