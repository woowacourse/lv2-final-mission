package finalmission.restaurant.repository;

import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.domain.RestaurantRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final JpaRestaurantRepository jpaRestaurantRepository;

    public RestaurantRepositoryImpl(final JpaRestaurantRepository jpaRestaurantRepository) {
        this.jpaRestaurantRepository = jpaRestaurantRepository;
    }

    @Override
    public Restaurant save(final Restaurant restaurant) {
        return jpaRestaurantRepository.save(restaurant);
    }
}
