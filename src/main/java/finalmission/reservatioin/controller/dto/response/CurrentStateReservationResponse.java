package finalmission.reservatioin.controller.dto.response;

import finalmission.reservatioin.entity.ReservationTime;
import finalmission.reservatioin.entity.ReservationWithNumberOfPeople;
import java.time.LocalDate;
import java.util.List;

public record CurrentStateReservationResponse(
        String storeName,
        LocalDate date,
        ReservationTime time,
        int numberOfReservation
) {

    public static List<CurrentStateReservationResponse> from(List<ReservationWithNumberOfPeople> all) {
        return all.stream()
                .map(CurrentStateReservationResponse::of)
                .toList();
    }

    public static CurrentStateReservationResponse of(ReservationWithNumberOfPeople reservationWithNumberOfPeople) {
        return new CurrentStateReservationResponse(
                reservationWithNumberOfPeople.getReservation().getOmakase().getStoreName(),
                reservationWithNumberOfPeople.getReservation().getReservationDate(),
                reservationWithNumberOfPeople.getReservation().getReservationTime(),
                (int) reservationWithNumberOfPeople.getRank()
        );
    }
}
