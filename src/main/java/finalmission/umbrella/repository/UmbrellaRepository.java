package finalmission.umbrella.repository;

import finalmission.umbrella.domain.Umbrella;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UmbrellaRepository extends JpaRepository<Umbrella, Long> {
}
