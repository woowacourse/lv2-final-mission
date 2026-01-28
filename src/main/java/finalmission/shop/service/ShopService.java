package finalmission.shop.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finalmission.shop.domain.OperatingHour;
import finalmission.shop.domain.Reservation;
import finalmission.shop.domain.Shop;
import finalmission.shop.dto.ReservationResponse;
import finalmission.shop.dto.ShopResponse;
import finalmission.shop.repository.OperatingHourRepository;
import finalmission.shop.repository.ReservationRepository;
import finalmission.shop.repository.ShopRepository;
import finalmission.user.domain.User;
import finalmission.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {

    private final ShopRepository shopRepository;
    private final OperatingHourRepository operatingHourRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public List<ShopResponse.Simple> getAll() {
        return shopRepository.findAll().stream()
                .map(ShopResponse.Simple::new)
                .toList();
    }

    public ShopResponse.Detail getDetail(Long id) {
        return new ShopResponse.Detail(shopRepository.getById(id));
    }

    public List<LocalTime> getAvailableTime(Long shopId, LocalDate date) {
        List<OperatingHour> operatingHour = operatingHourRepository.findAllByShopIdAndDayOfWeek(
                shopId, date.getDayOfWeek());
        if (operatingHour.isEmpty()) {
            return List.of();
        }

        // TODO 예약 테이블에서 꺼내와서 계산 필요

        return operatingHour.stream()
                .map(OperatingHour::getTime)
                .toList();
    }

    @Transactional
    public ReservationResponse.Created reserve(Long userId, Long shopId, LocalDate date, LocalTime time) {
        User user = userRepository.getById(userId);
        Shop shop = shopRepository.getById(shopId);
        validateReserveTime(date, time, shop);
        validateReserve(date, time, shop);

        Reservation reservation = new Reservation(user, date, time, shop);
        reservation = reservationRepository.save(reservation);
        return new ReservationResponse.Created(reservation);
    }

    private void validateReserve(LocalDate date, LocalTime time, Shop shop) {
        if (reservationRepository.existsByShopAndDateAndTime(shop, date, time)) {
            throw new IllegalArgumentException("이미 예약된 시간입니다.");
        }
    }

    private void validateReserveTime(LocalDate date, LocalTime time, Shop shop) {
        DayOfWeek today = date.getDayOfWeek();
        List<OperatingHour> operatingHours = shop.getOperatingHours();
        if (
                operatingHours.stream()
                        .filter(operatingHour -> Objects.equals(operatingHour.getDayOfWeek(), today))
                        .noneMatch(operatingHour -> operatingHour.getTime().equals(time))
        ) {
            throw new IllegalArgumentException("예약 불가능한 시간입니다.");
        }
    }

    @Transactional
    public void cancel(Long userId, Long reservationId) {
        User user = userRepository.getById(userId);
        Reservation reservation = reservationRepository.getById(reservationId);
        validateCancelReservation(reservation, user);
        reservationRepository.delete(reservation);
    }

    private void validateCancelReservation(Reservation reservation, User user) {
        if (reservation.getUser() != user) {
            throw new IllegalArgumentException("다른 사람의 예약은 취소할 수 없습니다.");
        }
    }

    public List<ReservationResponse.Simple> getMyReservations(Long userId) {
        User user = userRepository.getById(userId);
        return reservationRepository.findAllByUser(user).stream()
                .map(ReservationResponse.Simple::new)
                .toList();
    }
}
