package finalmission.reservation.service;

import finalmission.member.domain.Member;
import finalmission.member.repository.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationInformation;
import finalmission.reservation.repository.ReservationInformationRepository;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.reservation.service.dto.request.CreateReservationInformationRequest;
import finalmission.reservation.service.dto.response.ReservationDetailResponse;
import finalmission.reservation.service.dto.response.ReservationInformationResponse;
import finalmission.reservation.service.dto.request.CreateReservationRequest;
import finalmission.reservation.service.dto.response.ReservationResponse;
import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationInformationRepository reservationInformationRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;

    public ReservationService(
            final ReservationRepository reservationRepository,
            final ReservationInformationRepository reservationInformationRepository,
            final RestaurantRepository restaurantRepository,
            final MemberRepository memberRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationInformationRepository = reservationInformationRepository;
        this.restaurantRepository = restaurantRepository;
        this.memberRepository = memberRepository;
    }

    public List<ReservationResponse> getAllMyReservations(final Long memberId) {
        return reservationRepository.findAllByMemberId(memberId)
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @Transactional
    public ReservationDetailResponse getMyReservationDetail(final Long memberId, final Long reservationId) {
        Member member = getMember(memberId);
        validateOwnerOfReservation(reservationId, member);
        Reservation reservation = getReservationWithDetails(reservationId);
        return ReservationDetailResponse.from(reservation);
    }

    private void validateOwnerOfReservation(Long reservationId, Member member) {
        if (!isReservationOwner(reservationId, member)) {
            throw new IllegalStateException("접근 권한이 없습니다.");
        }
    }

    private boolean isReservationOwner(Long reservationId, Member member) {
        return reservationRepository.existsByCustomerAndId(member, reservationId);
    }

    private Reservation getReservationWithDetails(Long reservationId) {
        return reservationRepository.findWithDetailsById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
    }

    @Transactional
    public void deleteReservation(final Long memberId, final Long reservationId) {
        Member member = getMember(memberId);
        validateOwnerOfReservation(reservationId, member);
        reservationRepository.deleteById(reservationId);
    }

    @Transactional
    public ReservationResponse createReservation(CreateReservationRequest request, final Long memberId) {
        Member member = getMember(memberId);
        ReservationInformation restaurantInformation = getRestaurantInformation(request.reservationInformationId());
        Reservation saved = reservationRepository.save(new Reservation(restaurantInformation, member));
        return ReservationResponse.from(saved);
    }

    private ReservationInformation getRestaurantInformation(final Long id) {
        return reservationInformationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 예약 정보입니다."));
    }

    @Transactional
    public ReservationInformationResponse createReservationInformation(
            CreateReservationInformationRequest request,
            Long memberId
    ) {
        Restaurant restaurant = getRestaurant(request.restaurantId());
        Member member = getMember(memberId);

        validateOwner(restaurant, member);

        ReservationInformation saved = reservationInformationRepository.save(request.toEntity(restaurant));
        return ReservationInformationResponse.from(saved);
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
