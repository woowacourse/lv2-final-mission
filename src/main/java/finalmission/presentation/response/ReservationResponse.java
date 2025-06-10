package finalmission.presentation.response;

import finalmission.domain.reservation.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservationResponse(
        Long reservationId,
        CustomerResponse customer,
        LocalDate date,
        LocalTime time,
        DesignResponse design,
        DesignerResponse designer
) {

    public static List<ReservationResponse> fromReservations(final List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::fromReservation)
                .toList();
    }

    public static ReservationResponse fromReservation(final Reservation reservation) {
        return new ReservationResponse(
                reservation.getReservationId(),
                CustomerResponse.fromCustomer(reservation.getCustomer()),
                reservation.getDate(),
                reservation.getTime(),
                DesignResponse.fromDesign(reservation.getDesign()),
                DesignerResponse.fromDesigner(reservation.getDesigner())
        );
    }
}
