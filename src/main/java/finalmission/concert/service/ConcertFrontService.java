package finalmission.concert.service;

import finalmission.concert.domain.Concert;
import finalmission.concert.controller.dto.ConcertRequest;
import finalmission.concert.controller.dto.ConcertResponse;
import finalmission.concert.service.detail.ConcertCommandService;
import finalmission.concert.service.detail.ConcertQueryService;
import finalmission.venue.domain.Venue;
import finalmission.venue.service.detail.VenueQueryService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConcertFrontService {

    private final ConcertCommandService concertCommandService;
    private final ConcertQueryService concertQueryService;
    private final VenueQueryService venueQueryService;

    public ConcertResponse create(final ConcertRequest request) {
        final Venue venue = venueQueryService.get(request.venueId());
        final Concert concert = new Concert(
                request.title(),
                request.artist(),
                request.concertDate(),
                venue,
                request.price(),
                request.description()
        );

        final Concert savedConcert = concertCommandService.create(concert);
        return new ConcertResponse(
                savedConcert.getId(),
                savedConcert.getTitle(),
                savedConcert.getArtist(),
                savedConcert.getConcertDate(),
                savedConcert.getVenue().getId(),
                savedConcert.getPrice(),
                savedConcert.getDescription()
        );
    }

    public ConcertResponse get(Long id) {
        final Concert concert = concertQueryService.get(id);
        return new ConcertResponse(
                concert.getId(),
                concert.getTitle(),
                concert.getArtist(),
                concert.getConcertDate(),
                concert.getVenue().getId(),
                concert.getPrice(),
                concert.getDescription()
        );
    }

    public List<ConcertResponse> getAll() {
        return concertQueryService.getAll().stream()
                .map(concert -> new ConcertResponse(
                        concert.getId(),
                        concert.getTitle(),
                        concert.getArtist(),
                        concert.getConcertDate(),
                        concert.getVenue().getId(),
                        concert.getPrice(),
                        concert.getDescription()
                ))
                .toList();
    }

    public List<ConcertResponse> getConcertsCanReserve() {
        final LocalDateTime targetDateTime = LocalDateTime.now();
        return concertQueryService.getAllBefore(targetDateTime).stream()
                .map(concert -> new ConcertResponse(
                        concert.getId(),
                        concert.getTitle(),
                        concert.getArtist(),
                        concert.getConcertDate(),
                        concert.getVenue().getId(),
                        concert.getPrice(),
                        concert.getDescription()
                ))
                .toList();
    }
}
