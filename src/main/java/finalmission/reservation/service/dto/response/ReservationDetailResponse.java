package finalmission.reservation.service.dto.response;

import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationInformation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDetailResponse(
        String restaurant,
        LocalDate date,
        LocalTime time
) {
    public static ReservationDetailResponse from(Reservation reservation) {
        ReservationInformation information = reservation.getReservationInformation();
        return new ReservationDetailResponse(
                information.getRestaurant().getName(),
                information.getDate(),
                information.getStartAt()
        );
    }
}
