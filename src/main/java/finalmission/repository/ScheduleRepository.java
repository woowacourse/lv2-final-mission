package finalmission.repository;

import finalmission.domain.restaurant.Restaurant;
import finalmission.domain.schedule.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByRestaurant(Restaurant restaurant);
}
