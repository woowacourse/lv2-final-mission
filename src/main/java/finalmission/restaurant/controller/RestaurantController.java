package finalmission.restaurant.controller;

import finalmission.restaurant.controller.dto.request.RestaurantRequest;
import finalmission.restaurant.controller.dto.response.RestaurantResponse;
import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(final RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> create(@RequestBody @Valid RestaurantRequest request) {
        Restaurant restaurant = restaurantService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(RestaurantResponse.from(restaurant));
    }
}
