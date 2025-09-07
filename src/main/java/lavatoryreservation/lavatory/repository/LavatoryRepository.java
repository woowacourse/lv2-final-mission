package lavatoryreservation.lavatory.repository;

import lavatoryreservation.lavatory.domain.Lavatory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LavatoryRepository extends JpaRepository<Lavatory, Long> {

    boolean existsByDescription(String description);
}
