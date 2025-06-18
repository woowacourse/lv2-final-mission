package finalmission.venue.repository;

import finalmission.venue.domain.Venue;
import java.util.List;
import java.util.Optional;

public interface VenueRepository {

    Venue save(Venue venue);

    Optional<Venue> findById(Long id);

    List<Venue> findAll();
}
