package finalmission.repository;

import finalmission.entity.Reservation;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = """
                select r
                from Reservation r
                join fetch r.member
                join fetch r.meetingRoom
                join fetch r.reservationTime
            """)
    List<Reservation> findAllFetch();

    @Query(value = """
                select r
                from Reservation r
                join fetch r.member
                join fetch r.meetingRoom
                join fetch r.reservationTime
                where r.member.id = :memberId
            """)
    List<Reservation> findAllByMemberId(@Param("memberId") Long memberId);

    boolean existsByMeetingRoomId(Long id);

    boolean existsByReservationTimeId(Long reservationTimeId);

    boolean existsByMeetingRoomIdAndDateAndReservationTimeId(Long meetingRoomId,
                                                             LocalDate date,
                                                             Long reservationTimeId);

    boolean existsByIdAndMemberId(Long id, Long memberId);
}
