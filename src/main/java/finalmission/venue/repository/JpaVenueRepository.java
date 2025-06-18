package finalmission.venue.repository;

import finalmission.venue.domain.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaVenueRepository extends JpaRepository<Venue, Long>, VenueRepository {

}
