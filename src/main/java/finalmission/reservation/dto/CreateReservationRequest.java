package finalmission.reservation.dto;

import java.time.LocalDate;

public record CreateReservationRequest(LocalDate startDate,
                                       LocalDate endDate,
                                       String name,
                                       String phoneNumber,
                                       String email,
                                       long accommodationId) {
}
