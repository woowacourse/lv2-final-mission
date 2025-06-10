package finalmission.reservation.dto;

import finalmission.reservation.domain.Reservation;
import java.time.LocalDateTime;
import java.util.List;

public record SearchReservationResponse(List<SearchEachReservationResponse> responses) {

    protected record SearchEachReservationResponse(LocalDateTime time, String description, String roomName) {
    }

    public static SearchReservationResponse from(final List<Reservation> reservations) {
        return new SearchReservationResponse(reservations.stream()
                .map(reservation -> new SearchEachReservationResponse(reservation.getTime(), reservation.getDescription(),
                        reservation.getRoom().getName()))
                .toList());
    }
}
