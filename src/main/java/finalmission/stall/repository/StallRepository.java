package finalmission.stall.repository;

import finalmission.stall.entity.Stall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StallRepository extends JpaRepository<Stall, Long> {
    boolean existsByName(String stallName);
}
