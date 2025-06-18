package finalmission.restaurant.service;

import finalmission.restaurant.controller.dto.request.RestaurantRequest;
import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.repository.JpaRestaurantRepository;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private final JpaRestaurantRepository restaurantRepository;

    public RestaurantService(final JpaRestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant create(final RestaurantRequest request) {
        Restaurant restaurant = Restaurant.beforeSave(request.name(), request.description(), request.address(), request.zipcode(), request.tableCount());
        return restaurantRepository.save(restaurant);
    }
}
