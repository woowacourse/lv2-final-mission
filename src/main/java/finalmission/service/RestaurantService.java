package finalmission.service;

import finalmission.domain.restaurant.Restaurant;
import finalmission.domain.schedule.Schedule;
import finalmission.dto.restaurant.RestaurantDetailResponse;
import finalmission.dto.restaurant.RestaurantSimpleResponse;
import finalmission.dto.schedule.ScheduleResponse;
import finalmission.repository.ReservationRepository;
import finalmission.repository.RestaurantRepository;
import finalmission.repository.ScheduleRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ScheduleRepository scheduleRepository;
    private final ReservationRepository reservationRepository;

    public RestaurantService(final RestaurantRepository restaurantRepository,
                             final ScheduleRepository scheduleRepository,
                             final ReservationRepository reservationRepository) {
        this.restaurantRepository = restaurantRepository;
        this.scheduleRepository = scheduleRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RestaurantSimpleResponse> findAllRestaurant() {
        return restaurantRepository.findAll().stream()
                .map(RestaurantSimpleResponse::new)
                .toList();
    }

    public RestaurantDetailResponse findRestaurantById(final long id) {
        final Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[404] 존재하지 않는 예약입니다."));
        final List<Schedule> schedules = scheduleRepository.findByRestaurant(restaurant);
        final List<ScheduleResponse> scheduleResponses = schedules.stream().map(
                schedule -> new ScheduleResponse(schedule,
                        schedule.calculateRemainingCapacity(
                                reservationRepository.findBySchedule(schedule)
                        ))
        ).toList();
        return new RestaurantDetailResponse(restaurant, scheduleResponses);
    }
}
