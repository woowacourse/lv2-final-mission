package finalmission.station;

import java.util.Optional;
import finalmission.station.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
    Optional<Station> findByName(String name);
}
