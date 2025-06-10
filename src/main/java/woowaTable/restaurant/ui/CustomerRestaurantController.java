package woowaTable.restaurant.ui;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowaTable.restaurant.application.RestaurantService;
import woowaTable.restaurant.application.dto.ReservationRequest;
import woowaTable.restaurant.application.dto.ReservationResponse;
import woowaTable.restaurant.application.dto.RestaurantResponse;
import woowaTable.user.application.dto.LoginCheckRequest;

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

    @PostMapping("/restaurants")
    public ResponseEntity<ReservationResponse> reserve(
            final LoginCheckRequest loginCheckRequest,
            @Valid @RequestBody final ReservationRequest request
    ) {
        final ReservationResponse response = restaurantService.addReservation(loginCheckRequest, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
