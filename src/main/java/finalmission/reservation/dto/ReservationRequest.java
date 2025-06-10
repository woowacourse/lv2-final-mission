package finalmission.reservation.dto;

import java.time.LocalDate;

public record ReservationRequest(
        String userName,
        LocalDate reservationDate,
        Long reservationTimeId,
        Long meetingRoomId,
        int userCount
) {
}
