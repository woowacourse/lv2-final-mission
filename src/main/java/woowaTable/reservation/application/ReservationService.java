package woowaTable.reservation.application;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowaTable.reservation.application.dto.ReservationRequest;
import woowaTable.reservation.application.dto.ReservationResponse;
import woowaTable.reservation.domain.Reservation;
import woowaTable.restaurant.application.RestaurantQueryService;
import woowaTable.restaurant.domain.Restaurant;
import woowaTable.user.application.UserQueryService;
import woowaTable.user.application.dto.LoginCheckRequest;
import woowaTable.user.domain.Customer;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final UserQueryService userQueryService;
    private final RestaurantQueryService restaurantQueryService;
    private final ReservationCommandService reservationCommandService;

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
