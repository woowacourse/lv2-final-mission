package finalmission.seat.service.detail;

import finalmission.common.exception.NotFoundException;
import finalmission.concert.domain.Concert;
import finalmission.seat.domain.Seat;
import finalmission.seat.repository.SeatRepository;
import finalmission.seat.repository.vo.SeatWithReserved;
import finalmission.venue.domain.Venue;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SeatQueryService {

    private final SeatRepository seatRepository;

    public Seat get(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ID에 해당하는 좌석을 찾을 수 없습니다."));
    }

    public Seat getByVenueAndSeatNumber(final Venue venue, final Integer seatNumber) {
        return seatRepository.findByVenueAndSeatNumber(venue, seatNumber)
                .orElseThrow(() -> new NotFoundException("해당 공연장에 해당하는 좌석을 찾을 수 없습니다."));
    }

    public List<Seat> getAll() {
        return seatRepository.findAll();
    }

    public List<SeatWithReserved> getSeatsWithReserved(final Concert concert) {
        return seatRepository.findSeatsWithReservationStatusByIds(concert.getVenue().getId(), concert.getId());
    }
}
