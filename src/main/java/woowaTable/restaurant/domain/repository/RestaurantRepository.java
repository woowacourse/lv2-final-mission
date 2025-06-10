package woowaTable.restaurant.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import woowaTable.restaurant.domain.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findAllById(Long id);
}
