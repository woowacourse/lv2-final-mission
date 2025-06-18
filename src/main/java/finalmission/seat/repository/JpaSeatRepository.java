package finalmission.seat.repository;

import finalmission.seat.domain.Seat;
import finalmission.seat.repository.vo.SeatWithReserved;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaSeatRepository extends JpaRepository<Seat, Long>, SeatRepository {

    @Query("""
        SELECT new finalmission.seat.repository.vo.SeatWithReserved(
            s.id,
            s.seatNumber,
            EXISTS (SELECT r FROM Reservation r WHERE r.seat.id = s.id AND r.concert.id = :concertId)
        )
        FROM Seat s
        WHERE s.venue.id = :venueId
    """)
    List<SeatWithReserved> findSeatsWithReservationStatusByIds(
            @Param("venueId") Long venueId,
            @Param("concertId") Long concertId
    );
}
