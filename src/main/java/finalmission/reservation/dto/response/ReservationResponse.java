package finalmission.reservation.dto.response;

import finalmission.customer.dto.response.CustomerResponse;
import finalmission.reservation.domain.Reservation;
import finalmission.umbrella.dto.response.UmbrellaResponse;

import java.time.LocalDate;

public record ReservationResponse(long reservationId, CustomerResponse customerResponse, UmbrellaResponse umbrellaResponse, LocalDate reservationDate) {

    public static ReservationResponse from(Reservation reservation) {
        CustomerResponse customerResponse = new CustomerResponse(reservation.getCustomer().getId(), reservation.getCustomer().getEmail());
        UmbrellaResponse umbrellaResponse = new UmbrellaResponse(reservation.getUmbrella().getId());
        return new ReservationResponse(reservation.getId(), customerResponse, umbrellaResponse, reservation.getReservationDate());
    }
}
