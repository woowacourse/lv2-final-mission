package woowaTable.restaurant.application;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowaTable.restaurant.application.dto.ReservationRequest;
import woowaTable.restaurant.application.dto.ReservationResponse;
import woowaTable.restaurant.application.dto.RestaurantResponse;
import woowaTable.restaurant.domain.Reservation;
import woowaTable.restaurant.domain.Restaurant;
import woowaTable.user.application.UserQueryService;
import woowaTable.user.application.dto.LoginCheckRequest;
import woowaTable.user.domain.Customer;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantQueryService restaurantQueryService;
    private final ReservationCommandService reservationCommandService;
    private final UserQueryService userQueryService;

    public List<RestaurantResponse> findByOption(final Long region) {
        return restaurantQueryService.findByOption(region).stream()
                .map(RestaurantResponse::from)
                .toList();
    }

    public ReservationResponse addReservation(
            final LoginCheckRequest loginCheckRequest,
            final ReservationRequest reservationRequest
    ) {
        final Customer customer = userQueryService.findCustomerById(loginCheckRequest.id());
        final Restaurant restaurant = restaurantQueryService.findById(reservationRequest.id());
        final Reservation reservation = new Reservation(
                LocalDateTime.now(),
                restaurant,
                customer
        );
        return ReservationResponse.from(reservationCommandService.save(reservation));
    }
}
