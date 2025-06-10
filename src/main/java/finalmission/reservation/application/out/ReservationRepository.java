package finalmission.reservation.application.out;

import finalmission.popupstore.domain.PopupStore;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    int countByPopupStoreIdAndReservationStatus(
            Long id,
            ReservationStatus reservationStatus
    );

    List<Reservation> findAllByPopupStoreAndReservationStatus(
            PopupStore popupStore,
            ReservationStatus reservationStatus
    );

    List<Reservation> findAllByPopupStoreAndReservationStatusOrderByReservedAtAsc(
            PopupStore popupStore,
            ReservationStatus reservationStatus
    );
}
