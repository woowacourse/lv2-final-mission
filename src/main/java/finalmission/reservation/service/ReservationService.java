package finalmission.reservation.service;

import finalmission.member.domain.Member;
import finalmission.member.repository.MemberRepository;
import finalmission.reservation.domain.ReservationInformation;
import finalmission.reservation.repository.ReservationInformationRepository;
import finalmission.reservation.service.dto.CreateReservationInformationRequest;
import finalmission.reservation.service.dto.CreateReservationInformationResponse;
import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationInformationRepository reservationInformationRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;

    public ReservationService(
            final ReservationInformationRepository reservationInformationRepository,
            final RestaurantRepository restaurantRepository,
            final MemberRepository memberRepository
    ) {
        this.reservationInformationRepository = reservationInformationRepository;
        this.restaurantRepository = restaurantRepository;
        this.memberRepository = memberRepository;
    }

    public CreateReservationInformationResponse createReservationInformation(
            CreateReservationInformationRequest request,
            Long memberId
    ) {
        Restaurant restaurant = getRestaurant(request.restaurantId());
        Member member = getMember(memberId);

        validateOwner(restaurant, member);

        ReservationInformation saved = reservationInformationRepository.save(request.toEntity(restaurant));
        return CreateReservationInformationResponse.from(saved);
    }

    private Restaurant getRestaurant(final Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식당 정보입니다."));
    }

    private Member getMember(final Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    private void validateOwner(Restaurant restaurant, Member member) {
        if (!restaurant.isOwnedBy(member)) {
            throw new IllegalArgumentException("식당 소유자가 아닌 경우 이용할 수 없는 서비스입니다.");
        }
    }
}
