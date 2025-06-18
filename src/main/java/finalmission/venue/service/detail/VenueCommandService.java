package finalmission.venue.service.detail;

import finalmission.venue.domain.Venue;
import finalmission.venue.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VenueCommandService {

    private final VenueRepository venueRepository;

    public Venue create(final Venue venue) {
        return venueRepository.save(venue);
    }
}
