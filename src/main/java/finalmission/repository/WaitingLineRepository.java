package finalmission.repository;

import finalmission.domain.WaitingLine;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingLineRepository extends JpaRepository<WaitingLine, Long> {
    Optional<WaitingLine> findByStoreId(Long storeId);
}
