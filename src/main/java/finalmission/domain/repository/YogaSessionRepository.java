package finalmission.domain.repository;

import finalmission.domain.YogaSession;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YogaSessionRepository extends JpaRepository<YogaSession, Long> {

    List<YogaSession> findAllByDate(LocalDate date);
}
