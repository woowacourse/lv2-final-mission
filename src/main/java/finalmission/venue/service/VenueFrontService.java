package finalmission.venue.service;

import finalmission.venue.domain.Venue;
import finalmission.venue.controller.dto.VenueRequest;
import finalmission.venue.controller.dto.VenueResponse;
import finalmission.venue.service.detail.VenueCommandService;
import finalmission.venue.service.detail.VenueQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VenueFrontService {

    private final VenueCommandService venueCommandService;
    private final VenueQueryService venueQueryService;

    public VenueResponse create(final VenueRequest request) {
        Venue venue = new Venue(
                request.name(),
                request.address()
        );

        final Venue savedVenue = venueCommandService.create(venue);

        return new VenueResponse(
                savedVenue.getId(),
                savedVenue.getName(),
                savedVenue.getAddress()
        );
    }

    public VenueResponse get(Long id) {
        final Venue venue = venueQueryService.get(id);

        return new VenueResponse(
                venue.getId(),
                venue.getName(),
                venue.getAddress()
        );
    }

    public List<VenueResponse> getAll() {
        return venueQueryService.getAll().stream()
                .map(venue -> new VenueResponse(
                        venue.getId(),
                        venue.getName(),
                        venue.getAddress()
                ))
                .toList();
    }
}
