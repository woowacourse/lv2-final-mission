package finalmission.subway;

import finalmission.subway.domain.Subway;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubwayRepository extends JpaRepository<Subway, Long> {
}
