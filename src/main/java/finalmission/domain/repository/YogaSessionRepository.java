package finalmission.domain.repository;

import finalmission.domain.YogaSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YogaSessionRepository extends JpaRepository<YogaSession, Long> {
}
