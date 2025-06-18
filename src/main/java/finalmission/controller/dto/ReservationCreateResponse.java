package finalmission.controller.dto;

import finalmission.domain.NicknameReservation;

public record ReservationCreateResponse(long id, String name) {

    public ReservationCreateResponse(NicknameReservation reservation) {
        this(reservation.getId(), reservation.getNickname().getName());
    }
}
