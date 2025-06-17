package finalmission.mail.dto;

import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;

public record SendReservationEmailDto(String customerName,
                                      String customerEmail,
                                      String customerPhoneNumber,
                                      String accommodationName,
                                      LocalDate startDate,
                                      LocalDate endDate) {

    public static SendReservationEmailDto from(final Reservation reservation) {
        return new SendReservationEmailDto(
                reservation.getCustomer().getName(),
                reservation.getCustomer().getEmail(),
                reservation.getCustomer().getPhoneNumber(),
                reservation.getAccommodation().getName(),
                reservation.getStartDate(),
                reservation.getEndDate()
        );
    }
}
