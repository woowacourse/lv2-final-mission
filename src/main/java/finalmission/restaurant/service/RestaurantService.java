package finalmission.restaurant.service;

import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.domain.RestaurantRepository;
import finalmission.restaurant.dto.RestaurantRequest;
import finalmission.restaurant.dto.RestaurantResponse;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(final RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public RestaurantResponse save(RestaurantRequest restaurantRequest) {
        Restaurant savedRestaurant = restaurantRepository.save(restaurantRequest.toRestaurantEntity());
        return RestaurantResponse.from(savedRestaurant);
    }
}
