package woowaTable.restaurant.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowaTable.common.exception.error.NotFoundException;
import woowaTable.restaurant.domain.Restaurant;
import woowaTable.restaurant.domain.repository.RestaurantRepository;

@Service
@RequiredArgsConstructor
public class RestaurantQueryService {

    private final RestaurantRepository restaurantRepository;

    public List<Restaurant> findByOption(final Long region) {
        return restaurantRepository.findAllById(region);
    }

    public Restaurant findById(final Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당하는 식당이 없습니다."));
    }
}
