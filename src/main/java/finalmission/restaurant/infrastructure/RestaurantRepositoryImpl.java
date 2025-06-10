package finalmission.restaurant.infrastructure;

import finalmission.exception.resource.ResourceNotFoundException;
import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.domain.RestaurantRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final JpaRestaurantRepository jpaRepository;

    @Override
    public Restaurant save(final Restaurant restaurant) {
        return jpaRepository.save(restaurant);
    }

    @Override
    public void deleteById(final Long restaurantId) {
        jpaRepository.deleteById(restaurantId);
    }

    @Override
    public Restaurant getById(Long restaurantId) {
        return jpaRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 음식점이 존재하지 않습니다. id = " + restaurantId));
    }

    @Override
    public List<Restaurant> findAll() {
        return jpaRepository.findAll();
    }
}
