package finalmission.api.v1.restaurant.service;

import finalmission.api.v1.restaurant.dto.RestaurantResponse;
import finalmission.api.v1.restaurant.repository.RestaurantRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public List<RestaurantResponse> findAll() {
        return restaurantRepository.findAll().stream().map(RestaurantResponse::new).toList();

    }
}
