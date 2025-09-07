package lavatoryreservation.toilet.repository;

import lavatoryreservation.toilet.domain.Toilet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToiletRepository extends JpaRepository<Toilet, Long> {

    boolean existsByDescriptionAndLavatory_Id(String description, Long lavatoryId);
}
