package finalmission.seat.service;

import finalmission.concert.domain.Concert;
import finalmission.concert.service.detail.ConcertQueryService;
import finalmission.seat.controller.dto.SeatRequest;
import finalmission.seat.controller.dto.SeatResponse;
import finalmission.seat.domain.Seat;
import finalmission.seat.repository.vo.SeatWithReserved;
import finalmission.seat.service.detail.SeatCommandService;
import finalmission.seat.service.detail.SeatQueryService;
import finalmission.venue.domain.Venue;
import finalmission.venue.service.detail.VenueQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SeatFrontService {

    private final SeatCommandService seatCommandService;
    private final SeatQueryService seatQueryService;
    private final VenueQueryService venueQueryService;
    private final ConcertQueryService concertQueryService;

    public SeatResponse create(final SeatRequest request) {
        final Venue venue = venueQueryService.get(request.venueId());

        final Seat seat = new Seat(
                request.seatNumber(),
                venue
        );

        final Seat savedSeat = seatCommandService.create(seat);

        return new SeatResponse(
                savedSeat.getId(),
                savedSeat.getSeatNumber(),
                savedSeat.getVenue().getId()
        );
    }

    public SeatResponse get(Long id) {
        final Seat seat = seatQueryService.get(id);

        return new SeatResponse(
                seat.getId(),
                seat.getSeatNumber(),
                seat.getVenue().getId()
        );
    }

    public List<SeatResponse> getAll() {
        return seatQueryService.getAll().stream()
                .map(seat -> new SeatResponse(
                        seat.getId(),
                        seat.getSeatNumber(),
                        seat.getVenue().getId()
                ))
                .toList();
    }

    public List<SeatWithReserved> getSeatsWithReserved(final Long concertId) {
        final Concert concert = concertQueryService.get(concertId);
        return seatQueryService.getSeatsWithReserved(concert);
    }
}
