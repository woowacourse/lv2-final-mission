package finalmission.reservation.dto;

import finalmission.accommodation.dto.AccommodationResponse;
import finalmission.reservation.domain.CustomerInfo;
import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;

public record ReservationResponse(long id,
                                  LocalDate startDate,
                                  LocalDate endDate,
                                  long totalPrice,
                                  CustomerInfo customer,
                                  AccommodationResponse accommodation) {

    public static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getTotalPrice(),
                reservation.getCustomer(),
                AccommodationResponse.of(reservation.getAccommodation())
        );
    }
}
