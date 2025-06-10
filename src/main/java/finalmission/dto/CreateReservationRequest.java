package finalmission.dto;

import finalmission.domain.reservation.detail.ReservationDetail;
import finalmission.domain.reservation.owner.Owner;

public record CreateReservationRequest(
        String nickname,
        String email,
        String password,
        int numberOfGuest,
        String message
) {

    public Owner toOwner() {
        return new Owner(nickname, email, password);
    }

    public ReservationDetail toReservationDetail() {
        return new ReservationDetail(numberOfGuest, message);
    }
}
