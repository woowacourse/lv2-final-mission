package finalmission.shop.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import finalmission.shop.domain.Reservation;

public class ReservationResponse {

    private ReservationResponse() {
    }

    public record Created(
            Long id,
            Long userId,
            ShopResponse.Simple shop,
            LocalDate date,
            LocalTime time
    ) {

        public Created(Reservation reservation) {
            this(
                    reservation.getId(),
                    reservation.getUser().getId(),
                    new ShopResponse.Simple(reservation.getShop()),
                    reservation.getDate(),
                    reservation.getTime()
            );
        }
    }

    public record Simple(
            Long id,
            ShopResponse.Simple shop,
            LocalDate date,
            LocalTime time
    ) {

        public Simple(Reservation reservation) {
            this(
                    reservation.getId(),
                    new ShopResponse.Simple(reservation.getShop()),
                    reservation.getDate(),
                    reservation.getTime()
            );
        }
    }
}
