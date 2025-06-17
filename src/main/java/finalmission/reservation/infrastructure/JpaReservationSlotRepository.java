package finalmission.reservation.infrastructure;

import finalmission.reservation.domain.ReservationSlot;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservationSlotRepository extends JpaRepository<ReservationSlot, Long> {

    boolean existsByRestaurantId(Long restaurantId);

    Optional<ReservationSlot> findByRestaurantId(Long restaurantId);
}
