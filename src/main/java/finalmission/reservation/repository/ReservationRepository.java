package finalmission.reservation.repository;

import finalmission.meetingroom.domain.MeetingRoom;
import finalmission.member.domian.Member;
import finalmission.reservation.domain.Reservation;
import finalmission.reservationtime.dto.ReservationTimeAvailableResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByMember(Member member);

    boolean existsReservationByIdAndMemberId(Long id, Long memberId);

    List<Reservation> findByMemberId(Long memberId);

    @Query("""
        SELECT new finalmission.reservationtime.dto.ReservationTimeAvailableResponse(
            rt.id,
            rt.startAt,
            CASE WHEN r.id IS NOT NULL THEN true ELSE false END
        )
        FROM ReservationTime rt
        LEFT JOIN Reservation r
            ON r.time = rt AND r.date = :date AND r.meetingRoom = :room
""")
    List<ReservationTimeAvailableResponse> findAvailableTimes(LocalDate date, MeetingRoom room);
}
