package finalmission.omakase.repository;

import finalmission.omakase.entity.Omakase;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OmakaseJpaRepository extends JpaRepository<Omakase, Long> {
    Optional<Omakase> findByStoreName(String storeName);
}
