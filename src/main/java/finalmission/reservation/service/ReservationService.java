package finalmission.reservation.service;

import finalmission.auth.LoginMemberInfo;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.dto.CreateReservationRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.dto.UpdateReservationResponse;
import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.domain.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    public ReservationService(final ReservationRepository reservationRepository, final MemberRepository memberRepository, final RestaurantRepository restaurantRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public ReservationResponse save(CreateReservationRequest reservationRequest, LoginMemberInfo loginMemberInfo) {
        Member member = memberRepository.findById(loginMemberInfo.id())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 멤버 정보가 없습니다."));
        Restaurant restaurant = restaurantRepository.findById(reservationRequest.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 식당 정보가 없습니다."));
        Reservation reservation = new Reservation(null,
                reservationRequest.reservationDateTime(),
                member,
                restaurant,
                reservationRequest.personnel());
        return ReservationResponse.from(reservationRepository.save(reservation));
    }

    public List<ReservationResponse> findAll() {
        return ReservationResponse.from(reservationRepository.findAll());
    }

    public ReservationResponse findById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 예약 정보가 없습니다."));
        return ReservationResponse.from(reservation);
    }

    @Transactional
    public void deleteById(final Long reservationId, final LoginMemberInfo loginMemberInfo) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 예약 정보가 없습니다."));
        validateMemberOwnReservation(loginMemberInfo, reservation);

        reservationRepository.deleteById(reservationId);
    }

    @Transactional
    public ReservationResponse updateReservation(
            final Long reservationId,
            final UpdateReservationResponse reservationRequest,
            final LoginMemberInfo loginMemberInfo) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 예약 정보가 없습니다."));
        validateMemberOwnReservation(loginMemberInfo, reservation);
        reservation.updateReservation(reservationRequest.reservationDateTime(), reservationRequest.personnel());
        return ReservationResponse.from(reservation);
    }

    private void validateMemberOwnReservation(final LoginMemberInfo loginMemberInfo, final Reservation reservation) {
        Long memberId = loginMemberInfo.id();
        memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 멤버 정보가 없습니다."));

        if (!Objects.equals(reservation.getMember().getId(), memberId)) {
            throw new IllegalArgumentException("멤버 본인의 예약이 아닙니다.");
        }
    }

    public List<ReservationResponse> findByMemberId(final LoginMemberInfo loginMemberInfo) {
        List<Reservation> reservations = reservationRepository.findByMemberId(loginMemberInfo.id());
        return ReservationResponse.from(reservations);
    }
}
