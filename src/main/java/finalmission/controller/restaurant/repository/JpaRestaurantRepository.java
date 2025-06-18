package finalmission.controller.restaurant.repository;

import finalmission.controller.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRestaurantRepository extends JpaRepository<Restaurant,Long> {
}
