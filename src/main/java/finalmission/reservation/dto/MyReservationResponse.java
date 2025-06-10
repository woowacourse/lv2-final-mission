package finalmission.reservation.dto;

import finalmission.reservation.domain.Reservation;
import java.time.LocalDateTime;
import java.util.List;

public record MyReservationResponse(List<MyEachReservationResponse> responses) {

    protected record MyEachReservationResponse(LocalDateTime time, String description, String roomName) {
    }

    public static MyReservationResponse from(final List<Reservation> reservations) {
        return new MyReservationResponse(reservations.stream()
                .map(reservation -> new MyEachReservationResponse(reservation.getTime(), reservation.getDescription(),
                        reservation.getRoom().getName()))
                .toList());
    }
}
