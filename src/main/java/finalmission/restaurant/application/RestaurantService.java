package finalmission.restaurant.application;

import finalmission.exception.resource.ResourceInUseException;
import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.domain.RestaurantRepository;
import finalmission.restaurant.ui.dto.CreateRestaurantRequest;
import finalmission.restaurant.ui.dto.RestaurantResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public RestaurantResponse create(final CreateRestaurantRequest request) {
        final Restaurant restaurant = new Restaurant(
                request.name(),
                request.description(),
                request.place(),
                request.phoneNumber(),
                request.maxReservationCount()
        );
        final Restaurant saved = restaurantRepository.save(restaurant);

        return RestaurantResponse.from(saved);
    }

    @Transactional
    public void delete(final Long id) {
        restaurantRepository.getById(id);

        try {
            restaurantRepository.deleteById(id);
        } catch (final DataIntegrityViolationException e) {
            throw new ResourceInUseException("해당 음식점을 참조하고 있는 예약이 존재해서 삭제할 수 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<RestaurantResponse> findAll() {
        return restaurantRepository.findAll()
                .stream()
                .map(RestaurantResponse::from)
                .toList();
    }
}
