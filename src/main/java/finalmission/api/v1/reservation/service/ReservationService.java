package finalmission.api.v1.reservation.service;

import finalmission.api.v1.exception.NotFoundException;
import finalmission.api.v1.reservation.domain.Reservation;
import finalmission.api.v1.reservation.domain.ReservationTime;
import finalmission.api.v1.reservation.dto.ReservationRequest;
import finalmission.api.v1.reservation.dto.ReservationResponse;
import finalmission.api.v1.reservation.repository.ReservationRepository;
import finalmission.api.v1.reservation.repository.ReservationTimeRepository;
import finalmission.api.v1.restaurant.domain.Restaurant;
import finalmission.api.v1.restaurant.repository.RestaurantRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    @Transactional
    public ReservationResponse resisterReservation(final @Valid ReservationRequest request) {
        final ReservationTime reservationTime = reservationTimeRepository.findById(request.timeId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 시간입니다."));
        final Restaurant restaurant = restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 식당입니다."));
        final Reservation reservation = Reservation.builder()
                .phoneNumber(request.phoneNumber())
                .restaurant(restaurant)
                .build();
        final Reservation newReservation = reservationRepository.save(reservation);
        return new ReservationResponse(newReservation, request.date(), reservationTime.getTime());
    }
}
