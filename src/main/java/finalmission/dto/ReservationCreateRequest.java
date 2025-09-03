package finalmission.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(Long managerId,
                                       Long tourId,
                                       LocalDate date,
                                       LocalTime time) {
}
