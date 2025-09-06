package finalmission.cake.repository;

import finalmission.cake.model.Cake;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCakeRepository extends JpaRepository<Cake, Long>, CakeRepository {
}
