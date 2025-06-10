package finalmission.domain;

import java.util.UUID;
import org.springframework.data.repository.ListCrudRepository;

public interface GymRepository extends ListCrudRepository<Gym, UUID> {

}
