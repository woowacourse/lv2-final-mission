package finalmission.controller.dto;

import finalmission.domain.NicknameReservation;

public record ReservationResponse(long id, String name) {

    public ReservationResponse(NicknameReservation reservation) {
        this(reservation.getId(), reservation.getNickname().getName());
    }
}
