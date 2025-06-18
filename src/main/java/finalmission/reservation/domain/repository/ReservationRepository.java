package finalmission.reservation.domain.repository;

import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Query("""
            SELECT r FROM Reservation r
            WHERE (r.meetingRoom.id = :roomId)
            AND (r.date.value = :date)
            AND ((r.startAt.value between :startAt and :endAt)
                        OR r.endAt.value between :startAt and :endAt)
            
            """)
    List<Reservation> findReservationBy(Long roomId, LocalDate date, LocalTime startAt, LocalTime endAt);

    @Query("""
                    SELECT r FROM Reservation r
                    WHERE r.id != :reservationId
                    AND (r.meetingRoom.id = :roomId)
                    AND (r.date.value = :date)
                    AND ((r.startAt.value between :startAt and :endAt)
                                OR r.endAt.value between :startAt and :endAt)
            """)
    List<Reservation> findReservationByExcludingCurrent(Long reservationId, Long roomId, LocalDate date,
                                                        LocalTime startAt, LocalTime endAt);

}
