package woowaTable.restaurant.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowaTable.restaurant.domain.Restaurant;
import woowaTable.restaurant.domain.repository.RestaurantRepository;

@Service
@RequiredArgsConstructor
public class RestaurantQueryService {

    private final RestaurantRepository restaurantRepository;

    public List<Restaurant> findByOption(final Long region) {
        return restaurantRepository.findAllBy(region);
    }
}
