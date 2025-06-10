package finalmission;

import finalmission.dto.LoginMember;
import finalmission.dto.ReservationRegisterDto;
import finalmission.model.Member;
import finalmission.model.Reservation;
import finalmission.model.ReservationSchedule;
import finalmission.model.Seat;
import finalmission.presentation.service.ReservationService;
import finalmission.repository.MemberRepository;
import finalmission.repository.ReservationRepository;
import finalmission.repository.SeatRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
        seatRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("예약이 정상적으로 저장된다")
    void test1() {
        // given
        Member member = saveMember("example@gmail.com");
        Seat seat = saveSeat();
        ReservationRegisterDto reservationRegisterDto = new ReservationRegisterDto(
                seat.getId(),
                LocalDate.now().plusDays(1),
                LocalTime.of(12, 30),
                LocalTime.of(13, 30)
        );

        // when
        reservationService.registerReservation(reservationRegisterDto, new LoginMember(member.getId()));

        // then
        List<Reservation> foundReservation = reservationRepository.findAll();
        Reservation savedReservation = foundReservation.getFirst();
        assertAll(
                () -> assertThat(savedReservation.getMember().getName()).isEqualTo("히로"),
                () -> assertThat(savedReservation.getReservationDate()).isEqualTo(LocalDate.now().plusDays(1))
        );
    }

    @Test
    @DisplayName("예약 상세 조회 시 나의 예약이 아닌 경우 예외를 던진다")
    void test2() {
        // given
        Member member = saveMember("example@gmail.com");
        Member anotherMember = saveMember("new@gmail.com");
        Seat seat = saveSeat();
        Reservation reservation = saveReservation(anotherMember, seat);

        // when & then
        assertThatThrownBy(() -> reservationService.getReservation(reservation.getId(),
                new LoginMember(member.getId())))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("나의 예약이 아닌데 예약 수정을 시도하는 경우 예외를 던진다")
    void test3() {
        // given
        Member member = saveMember("example@gmail.com");
        Member anotherMember = saveMember("new@gmail.com");
        Seat seat = saveSeat();
        Reservation reservation = saveReservation(anotherMember, seat);
        // when & then
        assertThatThrownBy(() -> reservationService.updateReservation(reservation.getId(), null,
                new LoginMember(member.getId())))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 예약에 대한 수정 시도 시 예외가 발생한다")
    void test4() {
        // given
        Member member = saveMember("example@gmail.com");
        Member anotherMember = saveMember("new@gmail.com");
        Seat seat = saveSeat();

        // when & then
        assertThatThrownBy(() -> reservationService.updateReservation(123L, null,
                new LoginMember(member.getId())))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("나의 예약이 아닌데 예약 삭제를 시도하는 경우 예외를 던진다")
    void test5() {
        // given
        Member member = saveMember("example@gmail.com");
        Member anotherMember = saveMember("new@gmail.com");
        Seat seat = saveSeat();
        Reservation reservation = saveReservation(anotherMember, seat);

        // when & then
        assertThatThrownBy(() -> reservationService.updateReservation(reservation.getId(), null,
                new LoginMember(member.getId())))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 예약에 대한 삭제 시도 시 예외가 발생한다")
    void test6() {
        // given
        Member member = saveMember("example@gmail.com");
        Member anotherMember = saveMember("new@gmail.com");
        Seat seat = saveSeat();

        // when & then
        assertThatThrownBy(() -> reservationService.updateReservation(123L, null,
                new LoginMember(member.getId())))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약의 소유자가 예약을 삭제하는 경우 정상적으로 삭제된다")
    void test7() {
        // given
        Member member = saveMember("example@gmail.com");
        Seat seat = saveSeat();
        Reservation reservation = saveReservation(member, seat);

        // when
        reservationService.deleteReservation(reservation.getId(), new LoginMember(member.getId()));

        // then
        assertThat(reservationRepository.findById(reservation.getId())).isEmpty();
    }


    private Seat saveSeat() {
        return seatRepository.save(new Seat("백스윙", 1));
    }

    private Member saveMember(String email) {
        return memberRepository.save(new Member(
                "히로",
                email,
                "password"
        ));
    }

    private Reservation saveReservation(Member anotherMember, Seat seat) {
        return reservationRepository.save(new Reservation(
                anotherMember,
                seat,
                new ReservationSchedule(
                        LocalDate.now().plusDays(1),
                        LocalTime.of(12, 30),
                        LocalTime.of(13, 30)
                )
        ));
    }
}
