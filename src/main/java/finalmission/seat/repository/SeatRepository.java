package finalmission.seat.repository;

import finalmission.seat.domain.Seat;
import finalmission.seat.repository.vo.SeatWithReserved;
import finalmission.venue.domain.Venue;
import java.util.List;
import java.util.Optional;

public interface SeatRepository {

    Seat save(Seat seat);

    Optional<Seat> findById(Long id);

    List<Seat> findAll();

    List<SeatWithReserved> findSeatsWithReservationStatusByIds(Long venueId, Long concertId);

    Optional<Seat> findByVenueAndSeatNumber(Venue venue, Integer seatNumber);
}
