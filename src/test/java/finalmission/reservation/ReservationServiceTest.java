package finalmission.reservation;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import finalmission.line.domain.Line;
import finalmission.member.MemberRepository;
import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.ReservationRequest;
import finalmission.station.StationRepository;
import finalmission.subway.SubwayRepository;
import finalmission.subway.domain.Subway;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({ReservationService.class})
class ReservationServiceTest {
    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public ReservationServiceTest(ReservationService reservationService, ReservationRepository reservationRepository,
                                  MemberRepository memberRepository) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
    }

//    @DisplayName("Reservation 추가 테스트")
//    @Test
//    void createReservationTest() {
//        // given
//        ReservationRequest reservationRequest = new ReservationRequest(
//                LocalDate.now(),
//                1,
//                "A",
//                "신림",
//                "서울대입구"
//                );
//
//        // when
//        reservationService.createReservation(reservationRequest);
//
//        // then
//        boolean exist = reservationRepository.existsById(1L);
//        Assertions.assertThat(exist).isTrue();
//    }

//    @DisplayName("Reservation 삭제 테스트")
//    @Test
//    void deleteReservationTest() {
//        // given
//        Member member = new Member("01012345678");
//        memberRepository.save(member);
//
//        // when
//        reservationService.deleteReservation(member, 1L);
//
//        // then
//
//    }


}
