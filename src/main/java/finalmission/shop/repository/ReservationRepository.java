package finalmission.shop.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import finalmission.shop.domain.Reservation;
import finalmission.shop.domain.Shop;
import finalmission.user.domain.User;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findById(Long id);

    default Reservation getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
    }

    boolean existsByShopAndDateAndTime(Shop shop, LocalDate date, LocalTime time);

    List<Reservation> findAllByUser(User user);
}
