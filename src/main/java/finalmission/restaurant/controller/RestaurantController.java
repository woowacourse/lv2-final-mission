package finalmission.restaurant.controller;

import finalmission.restaurant.dto.RestaurantRequest;
import finalmission.restaurant.dto.RestaurantResponse;
import finalmission.restaurant.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(final RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/restaurants")
    public ResponseEntity<RestaurantResponse> createRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        RestaurantResponse restaurantResponse = restaurantService.save(restaurantRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantResponse);
    }
}

