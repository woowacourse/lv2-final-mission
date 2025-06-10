package finalmission.dto.request;

import java.time.LocalDate;

public record CreateReservationRequest(Long memberId, Long MeetingRoomId, LocalDate date, Long timeId) {
}
