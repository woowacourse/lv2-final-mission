package finalmission.reservation.application.out;

import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByReserverIdAndPopupStoreId(Long reserverId, Long popupStoreId);

    int countByPopupStoreIdAndReservationStatus(Long id, ReservationStatus reservationStatus);
}
