package finalmission.reservation.application.out.dto;

public record MyReservationWaitingCount(
        Long reservationId,
        int waitingCount
) {
}
