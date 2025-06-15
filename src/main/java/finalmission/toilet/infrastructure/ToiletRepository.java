package finalmission.toilet.infrastructure;

import finalmission.toilet.domain.Toilet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface ToiletRepository extends Repository<Toilet, Long> {
    List<Toilet> findAll();

    Optional<Toilet> findById(Long id);
}
