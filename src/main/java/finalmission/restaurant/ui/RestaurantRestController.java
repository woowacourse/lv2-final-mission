package finalmission.restaurant.ui;

import static finalmission.auth.domain.AuthRole.ADMIN;

import finalmission.auth.domain.RequiresRole;
import finalmission.restaurant.application.RestaurantService;
import finalmission.restaurant.ui.dto.CreateRestaurantRequest;
import finalmission.restaurant.ui.dto.RestaurantResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantRestController {

    private final RestaurantService restaurantService;

    @PostMapping
    @RequiresRole(authRoles = {ADMIN})
    public ResponseEntity<RestaurantResponse> create(
            @RequestBody @Valid final CreateRestaurantRequest request
    ) {
        final RestaurantResponse response = restaurantService.create(request);

        return ResponseEntity.created(URI.create("/restaurants/" + response.id()))
                .body(response);
    }

    @DeleteMapping({"/{id}"})
    @RequiresRole(authRoles = {ADMIN})
    public ResponseEntity<Void> delete(
            @PathVariable final Long id
    ) {
        restaurantService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> findAll() {
        final List<RestaurantResponse> restaurantResponses = restaurantService.findAll();

        return ResponseEntity.ok(restaurantResponses);
    }
}
