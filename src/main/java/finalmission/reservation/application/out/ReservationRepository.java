package finalmission.reservation.application.out;

import finalmission.popupstore.domain.PopupStore;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    int countByPopupStoreIdAndReservationStatus(
            Long id,
            ReservationStatus reservationStatus
    );

    List<Reservation> findAllByPopupStoreAndReservationStatusOrderByReservedAtAsc(
            PopupStore popupStore,
            ReservationStatus reservationStatus
    );

    @Query("""
                select r from Reservation r
                join fetch r.popupStore
                where r.popupStore = :popupStore
                  and r.reservationStatus = :reservationStatus
                order by r.reservedAt asc
            """)
    List<Reservation> findWithPopupStoreByPopupStoreAndReservationStatusOrderByReservedAtAsc(
            @Param("popupStore") PopupStore popupStore,
            @Param("reservationStatus") ReservationStatus reservationStatus
    );

    @Query("""
                select r from Reservation r
                join fetch r.popupStore
                join fetch r.reserver
                where r.id = :id
            """)
    Optional<Reservation> findWithPopupStoreAndReserverById(@Param("id") Long id);
}
