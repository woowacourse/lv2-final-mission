package finalmission.cake.repository;

import finalmission.cake.model.Cake;
import java.util.List;
import java.util.Optional;

public interface CakeRepository {

    List<Cake> findAllByIsAvailable(boolean isAvailable);

    Optional<Cake> findById(Long id);
}
