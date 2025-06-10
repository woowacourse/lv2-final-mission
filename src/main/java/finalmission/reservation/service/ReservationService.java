package finalmission.reservation.service;

import finalmission.member.domain.Member;
import finalmission.member.domain.Role;
import finalmission.member.repository.JpaMemberRepository;
import finalmission.reservation.controller.dto.request.ReservationRequest;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.Status;
import finalmission.reservation.repository.JpaReservationRepository;
import finalmission.reservationTime.domain.ReservationTime;
import finalmission.reservationTime.repository.JpaReservationTimeRepository;
import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.repository.JpaRestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final JpaReservationRepository reservationRepository;
    private final JpaMemberRepository memberRepository;
    private final JpaRestaurantRepository restaurantRepository;
    private final JpaReservationTimeRepository reservationTimeRepository;

    public ReservationService(
            final JpaReservationRepository reservationRepository,
            final JpaMemberRepository memberRepository,
            final JpaRestaurantRepository restaurantRepository,
            final JpaReservationTimeRepository reservationTimeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.restaurantRepository = restaurantRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public Reservation create(final ReservationRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));

        Restaurant restaurant = restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매장입니다."));

        ReservationTime reservationTime = reservationTimeRepository.findById(request.reservationTimeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간."));

        Reservation reservation = Reservation.beforeSave(
                member,
                restaurant,
                reservationTime,
                request.date(),
                request.visitor(),
                Status.DONE
        );
        return reservationRepository.save(reservation);
    }

    public List<Reservation> readAll() {
        return reservationRepository.findAll();
    }

    public List<Reservation> read(final long id) {
        return reservationRepository.findByMemberId(id);
    }
}
