package finalmission.reservation.repository;

import finalmission.reservation.controller.dto.ReservationDetailResponse;
import finalmission.reservation.domain.Reservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepository {

    @Query("""
                SELECT new finalmission.reservation.controller.dto.ReservationDetailResponse(
                    r.id,
                    r.concert.id,
                    r.concert.title,
                    r.concert.concertDate,
                    r.seat.id,
                    r.seat.seatNumber,
                    p.tid,
                    p.amount
                )
                FROM Reservation r
                LEFT JOIN Payment p ON p.reservation = r
                WHERE r.member.id = :memberId
            """)
    List<ReservationDetailResponse> findDetailsByMemberId(Long memberId);
}
