package finalmission.api.v1.restaurant.repository;

import finalmission.api.v1.restaurant.domain.Restaurant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findById(final Long id);
}
