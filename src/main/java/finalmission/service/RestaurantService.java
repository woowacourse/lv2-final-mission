package finalmission.service;

import finalmission.dto.RestaurantSimpleResponse;
import finalmission.repository.RestaurantRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;

    public RestaurantService(final RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantSimpleResponse> findAllRestaurant() {
        return restaurantRepository.findAll().stream()
                .map(RestaurantSimpleResponse::new)
                .toList();
    }
}
