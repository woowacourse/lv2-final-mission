package finalmission.reservation.presentation.dto.response;

import finalmission.medical.model.TreatmentType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationGetDetailWebResponse(Long id, String name, TreatmentType treatmentType, LocalDate date, LocalTime time, LocalDateTime createdAt) {
}
