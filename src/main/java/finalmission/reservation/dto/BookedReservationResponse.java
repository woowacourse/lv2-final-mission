package finalmission.reservation.dto;

import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;

public record BookedReservationResponse(LocalDate startDate,
                                        LocalDate endDate) {
    
    public static BookedReservationResponse of(Reservation reservation) {
        return new BookedReservationResponse(reservation.getStartDate(), reservation.getEndDate());
    }
}
