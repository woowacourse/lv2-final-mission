package finalmission.infrastructure;

import finalmission.domain.Toilet;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface ToiletRepository extends Repository<Toilet, Long> {

    Toilet save(Toilet toilet);

    List<Toilet> findAll();
}
