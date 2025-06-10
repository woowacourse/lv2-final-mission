package finalmission;

import finalmission.dto.LoginMember;
import finalmission.dto.ReservationRegisterDto;
import finalmission.model.Member;
import finalmission.model.Reservation;
import finalmission.model.Seat;
import finalmission.presentation.service.ReservationService;
import finalmission.repository.MemberRepository;
import finalmission.repository.ReservationRepository;
import finalmission.repository.SeatRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("예약이 정상적으로 저장된다")
    void test1() {
        // given
        Member member = memberRepository.save(new Member(
                "히로",
                "example@gmail.com",
                "password"
        ));
        Seat seat = seatRepository.save(new Seat("백스윙", 1));
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
}
