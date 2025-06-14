package finalmission.reservation.service;

import finalmission.auth.LoginMemberInfo;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.dto.CreateReservationRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.dto.UpdateReservationRequest;
import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.domain.RestaurantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class ReservationServiceTest {

    public static final LocalDateTime RESERVATION_DATE_TIME = LocalDateTime.now();
    public static final Member MEMBER1 = new Member(null, "name1", "email1@example.com", "password");
    public static final Member MEMBER2 = new Member(null, "name2", "email2@example.com", "password");
    public static final Restaurant RESTAURANT = new Restaurant(null, "name", "address", List.of("menu1"));

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @DisplayName("예약 생성 시 멤버 정보가 없으면 예외")
    void saveTest1() {
        Member member = memberRepository.save(MEMBER1);
        Restaurant restaurant = restaurantRepository.save(RESTAURANT);
        reservationRepository.save(new Reservation(null, RESERVATION_DATE_TIME, member, restaurant, 2));
        CreateReservationRequest reservationRequest = new CreateReservationRequest(RESERVATION_DATE_TIME, 1L, 2);
        LoginMemberInfo loginMemberInfo = new LoginMemberInfo(99L);

        assertThatThrownBy(() -> reservationService.save(reservationRequest, loginMemberInfo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 멤버 정보가 없습니다.");
    }

    @Test
    @DisplayName("예약 생성 시 식당 정보가 없으면 예외")
    void saveTest2() {
        Member member = memberRepository.save(MEMBER1);
        Restaurant restaurant = restaurantRepository.save(RESTAURANT);
        reservationRepository.save(new Reservation(null, RESERVATION_DATE_TIME, member, restaurant, 2));
        CreateReservationRequest reservationRequest = new CreateReservationRequest(RESERVATION_DATE_TIME, 99L, 2);
        LoginMemberInfo loginMemberInfo = new LoginMemberInfo(1L);

        assertThatThrownBy(() -> reservationService.save(reservationRequest, loginMemberInfo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 식당 정보가 없습니다.");
    }

    @Test
    @DisplayName("정상 예약 생성 테스트")
    void saveTest3() {
        Member member = memberRepository.save(MEMBER1);
        Restaurant restaurant = restaurantRepository.save(RESTAURANT);
        reservationRepository.save(new Reservation(null, RESERVATION_DATE_TIME, member, restaurant, 2));
        CreateReservationRequest reservationRequest = new CreateReservationRequest(RESERVATION_DATE_TIME, 1L, 2);
        LoginMemberInfo loginMemberInfo = new LoginMemberInfo(member.getId());

        ReservationResponse response = reservationService.save(reservationRequest, loginMemberInfo);

        assertAll(
                () -> assertThat(response.reservationDateTime()).isEqualTo(reservationRequest.reservationDateTime()),
                () -> assertThat(response.personnel()).isEqualTo(reservationRequest.personnel())
        );
    }

    @Test
    @DisplayName("조회 시 예약 id에 해당하는 예약 정보가 없으면 예외")
    void findByIdTest1() {
        assertThatThrownBy(() -> reservationService.findById(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 예약 정보가 없습니다.");
    }

    @Test
    @DisplayName("삭제 시 예약 id에 해당하는 예약 정보가 없으면 예외")
    void deleteByIdTest1() {
        Member member = memberRepository.save(MEMBER1);
        LoginMemberInfo loginMemberInfo = new LoginMemberInfo(member.getId());
        Restaurant restaurant = restaurantRepository.save(RESTAURANT);
        reservationRepository.save(new Reservation(null, RESERVATION_DATE_TIME, member, restaurant, 2));

        assertThatThrownBy(() -> reservationService.deleteById(99L, loginMemberInfo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 예약 정보가 없습니다.");
    }

    @Test
    @DisplayName("삭제 시 멤버 본인의 예약이 아니면 예외")
    void deleteByIdTest2() {
        Member member1 = memberRepository.save(MEMBER1);
        Member member2 = memberRepository.save(MEMBER2);
        LoginMemberInfo loginMemberInfo = new LoginMemberInfo(member2.getId());
        Restaurant restaurant = restaurantRepository.save(RESTAURANT);
        reservationRepository.save(new Reservation(null, RESERVATION_DATE_TIME, member1, restaurant, 2));

        assertThatThrownBy(() -> reservationService.deleteById(1L, loginMemberInfo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("멤버 본인의 예약이 아닙니다.");
    }

    @Test
    @DisplayName("정상 삭제 진행 테스트")
    void deleteByIdTest3() {
        Member member1 = memberRepository.save(MEMBER1);
        LoginMemberInfo loginMemberInfo = new LoginMemberInfo(member1.getId());
        Restaurant restaurant = restaurantRepository.save(RESTAURANT);
        Reservation reservation = reservationRepository.save(new Reservation(null, RESERVATION_DATE_TIME, member1, restaurant, 2));

        reservationService.deleteById(1L, loginMemberInfo);

        assertThat(reservationRepository.findById(reservation.getId()).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("수정 시 해당하는 예약이 없으면 예외")
    void updateReservationTest1() {
        Member member = memberRepository.save(MEMBER1);
        LoginMemberInfo loginMemberInfo = new LoginMemberInfo(member.getId());
        Restaurant restaurant = restaurantRepository.save(RESTAURANT);
        Reservation reservation = reservationRepository.save(new Reservation(null, RESERVATION_DATE_TIME, member, restaurant, 2));

        UpdateReservationRequest updateReservationRequest = new UpdateReservationRequest(reservation.getReservationDateTime(), reservation.getPersonnel());

        assertThatThrownBy(() -> reservationService.updateReservation(99L, updateReservationRequest, loginMemberInfo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 예약 정보가 없습니다.");
    }

    @Test
    @DisplayName("수정 시 멤버 본인의 예약이 아니면 예외")
    void updateReservationTest2() {
        Member member1 = memberRepository.save(MEMBER1);
        Member member2 = memberRepository.save(MEMBER2);
        LoginMemberInfo loginMemberInfo = new LoginMemberInfo(member2.getId());
        Restaurant restaurant = restaurantRepository.save(RESTAURANT);
        Reservation reservation = reservationRepository.save(new Reservation(null, RESERVATION_DATE_TIME, member1, restaurant, 2));

        UpdateReservationRequest updateReservationRequest = new UpdateReservationRequest(reservation.getReservationDateTime(), reservation.getPersonnel());

        assertThatThrownBy(() -> reservationService.updateReservation(reservation.getId(), updateReservationRequest, loginMemberInfo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("멤버 본인의 예약이 아닙니다.");
    }

    @Test
    @DisplayName("예약 수정 정상 진행 테스트")
    void updateReservationTest3() {
        Member member = memberRepository.save(MEMBER1);
        LoginMemberInfo loginMemberInfo = new LoginMemberInfo(member.getId());
        Restaurant restaurant = restaurantRepository.save(RESTAURANT);
        Reservation reservation = reservationRepository.save(new Reservation(null, RESERVATION_DATE_TIME, member, restaurant, 2));

        int toUpdatePersonnel = reservation.getPersonnel() + 2;
        UpdateReservationRequest updateReservationRequest = new UpdateReservationRequest(reservation.getReservationDateTime(), toUpdatePersonnel);
        ReservationResponse reservationResponse = reservationService.updateReservation(reservation.getId(), updateReservationRequest, loginMemberInfo);

        assertThat(reservationResponse.personnel()).isEqualTo(toUpdatePersonnel);
    }

    @Test
    @DisplayName("멤버 예약 조회시 해당하는 멤버 정보가 없으면 예외")
    void findByMemberId() {
        Member member = memberRepository.save(MEMBER1);
        Restaurant restaurant = restaurantRepository.save(RESTAURANT);
        reservationRepository.save(new Reservation(null, RESERVATION_DATE_TIME, member, restaurant, 2));
        LoginMemberInfo loginMemberInfo = new LoginMemberInfo(99L);

        assertThatThrownBy(() -> reservationService.findByMemberId(loginMemberInfo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 멤버 정보가 없습니다.");
    }
}