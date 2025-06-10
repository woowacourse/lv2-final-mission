package finalmission.umbrella.infrastructure;

import finalmission.umbrella.domain.Umbrella;
import finalmission.umbrella.domain.UmbrellaType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UmbrellaRepository extends JpaRepository<Umbrella, Long> {
    List<Umbrella> findByUmbrellaType(UmbrellaType umbrellaType);

    long countByUmbrellaType(UmbrellaType umbrellaType);
}
