package finalmission.api.v1.reservation.service;

import finalmission.api.v1.exception.NotFoundException;
import finalmission.api.v1.exception.ReservationException;
import finalmission.api.v1.openapi.OpenapiService;
import finalmission.api.v1.reservation.domain.Reservation;
import finalmission.api.v1.reservation.domain.ReservationTime;
import finalmission.api.v1.reservation.dto.ReservationDeleteRequest;
import finalmission.api.v1.reservation.dto.ReservationDetailGetRequest;
import finalmission.api.v1.reservation.dto.ReservationDetailResponse;
import finalmission.api.v1.reservation.dto.ReservationForAllUserResponse;
import finalmission.api.v1.reservation.dto.ReservationModifyRequest;
import finalmission.api.v1.reservation.dto.ReservationRequest;
import finalmission.api.v1.reservation.dto.ReservationResponse;
import finalmission.api.v1.reservation.repository.ReservationRepository;
import finalmission.api.v1.reservation.repository.ReservationTimeRepository;
import finalmission.api.v1.restaurant.domain.Restaurant;
import finalmission.api.v1.restaurant.repository.RestaurantRepository;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final OpenapiService openapiService;

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public List<ReservationForAllUserResponse> getAllReservation() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationForAllUserResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReservationDetailResponse getReservationDetail(final Long id, final ReservationDetailGetRequest request) {
        final Reservation reservation = getReservation(id);
        if (reservation.isSamePhoneNumber(request.phoneNumber())) {
            return new ReservationDetailResponse(reservation, reservation.getDate(), reservation.getReservationTime().getTime());
        }
        throw new ReservationException("내 예약만 조회가 가능합니다.");
    }

    @Transactional
    public ReservationResponse resisterReservation(final @Valid ReservationRequest request) {
        final ReservationTime reservationTime = reservationTimeRepository.findById(request.timeId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 시간입니다."));
        final Restaurant restaurant = restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 식당입니다."));

        final String nickname = openapiService.getNickname();

        final Reservation reservation = Reservation.builder()
                .phoneNumber(request.phoneNumber())
                .nickname(nickname)
                .restaurant(restaurant)
                .date(request.date())
                .reservationTime(reservationTime)
                .build();
        final Reservation newReservation = reservationRepository.save(reservation);
        return new ReservationResponse(newReservation);
    }

    @Transactional
    public void deleteReservation(final Long id, final ReservationDeleteRequest request) {
        final Reservation reservation = getReservation(id);
        if (reservation.isSamePhoneNumber(request.phoneNumber())) {
            reservationRepository.deleteById(id);
            return;
        }
        throw new ReservationException("내 예약만 삭제할 수 있습니다.");
    }

    @Transactional
    public ReservationResponse modifyReservation(final Long id, final ReservationModifyRequest request) {
        final Reservation reservation = getReservation(id);
        if (reservation.isSamePhoneNumber(request.phoneNumber())) {
            reservation.modifyPhoneNumber(request.newPhoneNumber());
            return new ReservationResponse(reservation);
        }
        throw new ReservationException("내 예약만 수정할 수 있습니다.");

    }

    private Reservation getReservation(final Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 예약입니다."));
    }
}
