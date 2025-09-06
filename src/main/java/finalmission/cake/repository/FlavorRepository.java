package finalmission.cake.repository;

import finalmission.cake.model.Flavor;
import java.util.List;
import java.util.Optional;

public interface FlavorRepository {
    List<Flavor> findAll();

    Optional<Flavor> findById(Long id);
}
