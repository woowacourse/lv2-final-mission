package finalmission.reservation.service;

import finalmission.auth.controller.dto.request.LoginMember;
import finalmission.member.domain.Member;
import finalmission.member.repository.JpaMemberRepository;
import finalmission.reservation.controller.dto.request.ReservationRequest;
import finalmission.reservation.controller.dto.response.ReservationResponse;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.Status;
import finalmission.reservation.repository.JpaReservationRepository;
import finalmission.reservationTime.domain.ReservationTime;
import finalmission.reservationTime.repository.JpaReservationTimeRepository;
import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.repository.JpaRestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Reservation create(final ReservationRequest request, final LoginMember loginMember) {
        Member member = memberRepository.findById(loginMember.id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));

        Restaurant restaurant = getRestaurant(request.restaurantId());
        ReservationTime reservationTime = getReservationTime(request.reservationTimeId());

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

    public List<Reservation> findReservationsByMemberId(final long id) {
        return reservationRepository.findByMemberId(id);
    }

    public ReservationResponse update(final Long id, final ReservationRequest request, final LoginMember loginMember) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 예약이 존재하지 않습니다."));

        validateOwner(reservation.getMember(), loginMember);
        Restaurant restaurant = getRestaurant(request.restaurantId());
        ReservationTime reservationTime = getReservationTime(request.reservationTimeId());

        reservation.update(restaurant, reservationTime, request.date(), request.visitor());
        return ReservationResponse.from(reservation);
    }

    private void validateOwner(final Member member, final LoginMember loginMember) {
        if (!member.getId().equals(loginMember.id())) {
            throw new IllegalArgumentException("본인의 예약만 변경 및 삭제가 가능합니다.");
        }
    }

    private Restaurant getRestaurant(final long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매장입니다."));
    }

    private ReservationTime getReservationTime(final long reservationTimeId) {
        return reservationTimeRepository.findById(reservationTimeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간."));
    }

    public void delete(final long id, final LoginMember loginMember) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약이 존재하지 않습니다."));
        validateOwner(reservation.getMember(),loginMember);
        reservationRepository.delete(reservation);
    }
}
