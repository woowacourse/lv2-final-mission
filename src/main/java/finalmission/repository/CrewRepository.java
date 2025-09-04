package finalmission.repository;

import finalmission.domain.Crew;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewRepository extends JpaRepository<Crew, Long> {

    Optional<Crew> findByEmailAndPassword(String email, String password);
}
