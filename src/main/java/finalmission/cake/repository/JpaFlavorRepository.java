package finalmission.cake.repository;

import finalmission.cake.model.Flavor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFlavorRepository extends FlavorRepository, JpaRepository<Flavor, Long> {

}
