package finalmission.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.contains;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import finalmission.client.MailClient;
import finalmission.domain.Car;
import finalmission.domain.Member;
import finalmission.domain.MemberCredential;
import finalmission.domain.Rent;
import finalmission.domain.repository.CarRepository;
import finalmission.domain.repository.MemberCredentialRepository;
import finalmission.domain.repository.MemberRepository;
import finalmission.domain.repository.RentRepository;
import finalmission.dto.RequestRent;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class RentServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberCredentialRepository credentialRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private RentRepository rentRepository;
    @Mock
    private MailClient mailClient;

    private RentService rentService;

    @BeforeEach
    void setUp() {
        rentService = new RentService(carRepository, rentRepository, credentialRepository, mailClient);
    }

    @Test
    @DisplayName("정상 예약 시 렌트가 저장되고 메일이 전송된다")
    void rentSuccess() {
        // given
        Member member = createMember("user@example.com");
        Car car = createCar("Model X");
        RequestRent request = createRequestRent(car.getId(), LocalDate.now().plusDays(1), 9, 12);

        // when
        Long rentId = rentService.rent(member, request);

        // then
        Rent rent = rentRepository.findById(rentId).orElseThrow();
        assertThat(rent.getCar().getId()).isEqualTo(car.getId());
        assertThat(rent.getMember().getId()).isEqualTo(member.getId());
        verify(mailClient).send(
                eq("user@example.com"),
                eq("렌트 예약 확인"),
                contains("차량: Model X")
        );
    }

    @Test
    @DisplayName("같은 시간에 같은 차량 중복 예약 시 예외 발생")
    void rentTimeConflictThrows() {
        // given
        Member member = createMember("user@example.com");
        Car car = createCar("K3");
        LocalDate date = LocalDate.now().plusDays(1);

        RequestRent request = createRequestRent(car.getId(), date, 10, 12);
        rentService.rent(member, request);

        // when
        // then
        assertThatThrownBy(() -> rentService.rent(member, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 예약된 차량");
        verify(mailClient, times(1)).send(any(), any(), any());
    }

    @Test
    @DisplayName("한 사용자가 같은 시간에 여러 차량을 예약하면 예외 발생")
    void multipleCarConflictForSameUser() {
        // given
        Member member = createMember("user@example.com");
        Car car1 = createCar("K5");
        Car car2 = createCar("K7");
        LocalDate date = LocalDate.now().plusDays(1);
        RequestRent rent1 = createRequestRent(car1.getId(), date, 10, 12);
        RequestRent rent2 = createRequestRent(car2.getId(), date, 10, 12);
        rentService.rent(member, rent1);

        // when
        // then
        assertThatThrownBy(() -> rentService.rent(member, rent2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 시간대에 2대 이상의 차량을 예약할 수 없습니다.");
        verify(mailClient, times(1)).send(any(), any(), any());
    }

    @Test
    @DisplayName("존재하지 않는 차량 ID로 예약 시 예외 발생")
    void rentWithInvalidCarIdThrows() {
        // given
        Member member = createMember("user@example.com");
        RequestRent request = createRequestRent(999L, LocalDate.now().plusDays(1), 9, 11);

        // when
        // then
        assertThatThrownBy(() -> rentService.rent(member, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공유 차량이 존재하지 않습니다.");
        verifyNoInteractions(mailClient);
    }

    @Test
    @DisplayName("회원 이메일 정보가 없으면 예외 발생")
    void missingEmailThrows() {
        // given
        Member member = memberRepository.save(Member.builder().name("홍길동").build());
        Car car = createCar("Avante");
        RequestRent request = createRequestRent(car.getId(), LocalDate.now().plusDays(1), 10, 12);

        // when
        // then
        assertThatThrownBy(() -> rentService.rent(member, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("회원 정보를 찾을 수 없습니다.");
        verifyNoInteractions(mailClient);
    }

    @Test
    @DisplayName("사용자는 자신의 예약 목록을 조회할 수 있다")
    void getRentsByMember() {
        // given
        Member member = createMember("me@example.com");
        Car car = createCar("Morning");
        RequestRent request = createRequestRent(car.getId(), LocalDate.now().plusDays(1), 8, 10);
        rentService.rent(member, request);

        // when
        var result = rentService.getAllByMember(member);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).car().name()).isEqualTo("Morning");
    }

    @Test
    @DisplayName("본인의 예약만 취소할 수 있다")
    void cancelOwnRentSuccess() {
        // given
        Member member = createMember("me@example.com");
        Car car = createCar("Ray");
        RequestRent request = createRequestRent(car.getId(), LocalDate.now().plusDays(1), 9, 11);
        Long rentId = rentService.rent(member, request);

        // when
        rentService.cancelById(member, rentId);

        // then
        assertThat(rentRepository.findById(rentId)).isEmpty();
    }

    @Test
    @DisplayName("다른 사용자의 예약을 취소하면 예외 발생")
    void cancelOthersRentThrows() {
        // given
        Member owner = createMember("owner@example.com");
        Member other = createMember("other@example.com");
        Car car = createCar("SM6");
        RequestRent request = createRequestRent(car.getId(), LocalDate.now().plusDays(1), 9, 11);
        Long rentId = rentService.rent(owner, request);

        // when & then
        assertThatThrownBy(() -> rentService.cancelById(other, rentId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("본인 예약만 취소할 수 있습니다.");
        assertThat(rentRepository.findById(rentId)).isPresent();
    }

    private Member createMember(String email) {
        Member member = memberRepository.save(Member.builder().name("홍길동").build());
        credentialRepository.save(MemberCredential.builder()
                .email(email)
                .password("password")
                .member(member)
                .build());
        return member;
    }

    private Car createCar(String name) {
        return carRepository.save(Car.builder()
                .name(name)
                .licensePlate("123하4567")
                .feePerMinute(200L)
                .build());
    }

    private RequestRent createRequestRent(Long carId, LocalDate date, int startHour, int returnHour) {
        return new RequestRent(
                carId,
                date,
                LocalTime.of(startHour, 0),
                LocalTime.of(returnHour, 0)
        );
    }
}
