package finalmission.controller;

import finalmission.dto.RestaurantSimpleResponse;
import finalmission.service.RestaurantService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(final RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantSimpleResponse>> findAllRestaurants() {
        final List<RestaurantSimpleResponse> response = restaurantService.findAllRestaurant();
        return ResponseEntity.ok().body(response);
    }
}
