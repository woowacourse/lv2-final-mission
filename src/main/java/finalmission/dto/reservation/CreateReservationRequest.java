package finalmission.dto.reservation;

import finalmission.domain.reservation.detail.ReservationDetail;
import finalmission.domain.reservation.owner.Nickname;
import finalmission.domain.reservation.owner.Owner;

public record CreateReservationRequest(
        String email,
        String password,
        int numberOfGuest,
        String message
) {

    public Owner toOwnerWithRandomNickname(final Nickname nickname) {
        return new Owner(nickname, email, password);
    }

    public ReservationDetail toReservationDetail() {
        return new ReservationDetail(numberOfGuest, message);
    }
}
