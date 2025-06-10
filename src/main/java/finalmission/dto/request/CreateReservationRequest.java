package finalmission.dto.request;

import java.time.LocalDate;

public record CreateReservationRequest(Long MeetingRoomId, LocalDate date, Long timeId) {
}
