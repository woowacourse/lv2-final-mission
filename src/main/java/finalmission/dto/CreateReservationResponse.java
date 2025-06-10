package finalmission.dto;

import finalmission.domain.reservation.Reservation;

public record CreateReservationResponse(
        long id,
        int numberOfGuest,
        String email,
        String message,
        String nickname,
        String password,
        ScheduleResponse schedule
) {
    public CreateReservationResponse(final Reservation reservation, final int numberOfGuest) {
        this(
                reservation.getId(),
                reservation.getNumberOfGuest().getValue(),
                reservation.getEmail().getValue(),
                reservation.getReservatonMessage().getMessage(),
                reservation.getNickname().getValue(),
                reservation.getPassword().getValue(),
                new ScheduleResponse(reservation.getSchedule(), numberOfGuest)
        );
    }
}
