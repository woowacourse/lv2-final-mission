package shh.stall.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shh.stall.domain.Stall;

public interface StallRepository extends JpaRepository<Stall, Long> {
}
