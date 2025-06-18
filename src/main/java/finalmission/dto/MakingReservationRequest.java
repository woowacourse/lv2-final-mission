package finalmission.dto;

import java.time.LocalDate;

public record MakingReservationRequest(LocalDate date,Long roomId,Long reservationTimeId) {

}
