package woowaTable.restaurant.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowaTable.restaurant.application.RestaurantService;
import woowaTable.restaurant.application.dto.RestaurantResponse;

@RestController
@RequiredArgsConstructor
public class CustomerRestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/restaurants/search")
    public ResponseEntity<List<RestaurantResponse>> getFilteredRestaurants(
            @RequestParam(required = false, name = "region") final Long region
    ) {
        final List<RestaurantResponse> responses = restaurantService.findByOption(region);
        return ResponseEntity.ok(responses);
    }
}
