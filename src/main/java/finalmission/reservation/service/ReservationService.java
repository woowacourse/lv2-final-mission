package finalmission.reservation.service;

import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.dto.CreateReservationRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.domain.RestaurantRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    public ReservationService(final ReservationRepository reservationRepository, final MemberRepository memberRepository, final RestaurantRepository restaurantRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public ReservationResponse save(CreateReservationRequest reservationRequest) {
        Member member = memberRepository.findById(reservationRequest.memberId())
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
}
