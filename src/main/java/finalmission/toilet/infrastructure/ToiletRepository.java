package finalmission.toilet.infrastructure;

import finalmission.toilet.domain.Toilet;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface ToiletRepository extends Repository<Toilet, Long> {
    List<Toilet> findAll();
}
