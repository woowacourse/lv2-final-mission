package finalmission.reservatioin.controller.dto.response;

import finalmission.reservatioin.entity.Reservation;
import finalmission.reservatioin.entity.ReservationTime;
import java.time.LocalDate;
import java.util.List;

public record ReservationResponse(
        String nickName,
        String storeName,
        ReservationTime reservationTime,
        LocalDate reservationDate
) {

    public static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(
                reservation.getCustomer().getNickName(),
                reservation.getOmakase().getStoreName(),
                reservation.getReservationTime(),
                reservation.getReservationDate()
        );
    }

    public static List<ReservationResponse> from(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::of)
                .toList();
    }
}
