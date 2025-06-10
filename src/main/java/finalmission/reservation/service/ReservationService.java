package finalmission.reservation.service;

import finalmission.auth.LoginMemberInfo;
import finalmission.client.HolidayClient;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.dto.CreateReservationRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.domain.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;
    private final HolidayClient holidayClient;

    public ReservationService(final ReservationRepository reservationRepository, final MemberRepository memberRepository, final RestaurantRepository restaurantRepository, final HolidayClient holidayClient) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.restaurantRepository = restaurantRepository;
        this.holidayClient = holidayClient;
    }

    public ReservationResponse save(CreateReservationRequest reservationRequest, LoginMemberInfo loginMemberInfo) {
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 멤버 정보가 없습니다."));
        Restaurant restaurant = restaurantRepository.findById(reservationRequest.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 식당 정보가 없습니다."));
        Object holiday = holidayClient.getHoliday();
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

    public void deleteById(final Long reservationId, final Long memberId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 예약 정보가 없습니다."));

        if (!Objects.equals(reservation.getMember().getId(), memberId)) {
            throw new IllegalArgumentException("멤버 본인의 예약이 아닙니다.");
        }

        reservationRepository.deleteById(reservationId);
    }
}
