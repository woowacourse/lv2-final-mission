package finalmission.shop.repository;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import finalmission.shop.domain.OperatingHour;

public interface OperatingHourRepository extends JpaRepository<OperatingHour, Long> {

    List<OperatingHour> findAllByShopIdAndDayOfWeek(Long shopId, DayOfWeek dayOfWeek);
}
