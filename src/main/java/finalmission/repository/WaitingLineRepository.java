package finalmission.repository;

import finalmission.domain.WaitingLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingLineRepository extends JpaRepository<WaitingLine, Long> {
}
