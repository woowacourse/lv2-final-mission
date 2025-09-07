package lavatoryreservation.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lavatoryreservation.lavatory.domain.Lavatory;
import lavatoryreservation.lavatory.domain.Sex;
import lavatoryreservation.lavatory.repository.LavatoryRepository;
import lavatoryreservation.lavatory.service.LavatoryService;
import lavatoryreservation.member.dto.SignupDto;
import lavatoryreservation.member.repository.MemberRepository;
import lavatoryreservation.member.service.MemberService;
import lavatoryreservation.reservation.domain.Reservation;
import lavatoryreservation.reservation.dto.AddReservationDto;
import lavatoryreservation.reservation.dto.DeleteReservationDto;
import lavatoryreservation.reservation.repository.ReservationRepository;
import lavatoryreservation.toilet.dto.AddToiletDto;
import lavatoryreservation.toilet.repository.ToiletRepository;
import lavatoryreservation.toilet.service.ToiletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTest {

    @Autowired
    LavatoryService lavatoryService;
    @Autowired
    ToiletService toiletService;
    @Autowired
    ReservationService reservationService;
    @Autowired
    MemberService memberService;

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    LavatoryRepository lavatoryRepository;
    @Autowired
    ToiletRepository toiletRepository;
    @Autowired
    MemberRepository memberRepository;


    @BeforeEach
    void cleanUp() {
        reservationRepository.deleteAll();
        toiletRepository.deleteAll();
        lavatoryRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void 화장실과_화장실칸에_대해서_예약을할_수_있다() {
        Lavatory lavatory = lavatoryService.addLavatory(new Lavatory(null, Sex.MEN, "잠실굿샷남자화장실"));
        Long toiletId = toiletService.addToilet(new AddToiletDto(null, false, lavatory.getId()));
        Long memberId = memberService.addMember(new SignupDto("투다", "praisebak@naver.com", Sex.MEN));
        LocalDate date = LocalDate.now().plusDays(1L);
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(8, 0);

        Long id = reservationService.addReservation(
                new AddReservationDto(memberId, toiletId, LocalDateTime.of(date, startTime),
                        LocalDateTime.of(date, endTime)));
        assertThat(reservationRepository.findById(id)).isPresent();
    }

    @Test
    void 화장실은_같은_성별이아니면_예약이_불가능하다() {
        Lavatory lavatory = lavatoryService.addLavatory(new Lavatory(null, Sex.MEN, "잠실굿샷남자화장실"));
        Long toiletId = toiletService.addToilet(new AddToiletDto(null, false, lavatory.getId()));
        Long memberId = memberService.addMember(new SignupDto("투다", "praisebak@naver.com", Sex.WOMEN));
        LocalDate date = LocalDate.now().plusDays(1L);
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(8, 0);

        assertThatThrownBy(() -> reservationService.addReservation(
                new AddReservationDto(memberId, toiletId, LocalDateTime.of(date, startTime),
                        LocalDateTime.of(date, endTime)))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 자신의_화장실_예약을_제거할_수_있다() {
        Lavatory lavatory = lavatoryService.addLavatory(new Lavatory(null, Sex.MEN, "잠실굿샷남자화장실"));
        Long toiletId = toiletService.addToilet(new AddToiletDto(null, false, lavatory.getId()));
        Long memberId = memberService.addMember(new SignupDto("투다", "praisebak@naver.com", Sex.MEN));
        LocalDate date = LocalDate.now().plusDays(1L);
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(8, 0);
        Long reservationId = reservationService.addReservation(
                new AddReservationDto(memberId, toiletId, LocalDateTime.of(date, startTime),
                        LocalDateTime.of(date, endTime)));

        assertThat(reservationRepository.count()).isEqualTo(1L);

        reservationService.deleteReservation(new DeleteReservationDto(reservationId), memberId);

        assertThat(reservationRepository.count()).isEqualTo(0L);

    }

    @Test
    void 타인의_화장실_예약을_제거할_수_없다() {
        Lavatory lavatory = lavatoryService.addLavatory(new Lavatory(null, Sex.MEN, "잠실굿샷남자화장실"));
        Long toiletId = toiletService.addToilet(new AddToiletDto(null, false, lavatory.getId()));
        Long memberId = memberService.addMember(new SignupDto("투다", "praisebak@naver.com", Sex.MEN));
        LocalDate date = LocalDate.now().plusDays(1L);
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(8, 0);
        reservationService.addReservation(new AddReservationDto(memberId, toiletId, LocalDateTime.of(date, startTime),
                LocalDateTime.of(date, endTime)));

        Long memberId2 = memberService.addMember(new SignupDto("빙봉", "praisebak2@naver.com", Sex.MEN));

        assertThatThrownBy(() -> reservationService.deleteReservation(new DeleteReservationDto(toiletId), memberId2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 화장실은_연속으로_예약할_수_없다() {
        Lavatory lavatory = lavatoryService.addLavatory(new Lavatory(null, Sex.MEN, "잠실굿샷남자화장실"));
        Long toiletId = toiletService.addToilet(new AddToiletDto(null, false, lavatory.getId()));
        Long memberId = memberService.addMember(new SignupDto("투다", "praisebak@naver.com", Sex.MEN));
        LocalDate date = LocalDate.now().plusDays(1L);
        LocalTime startTime = LocalTime.of(11, 0);
        LocalTime endTime = LocalTime.of(11, 30);

        LocalTime nextTime = LocalTime.of(11, 30);
        LocalTime nextTimeEnd = LocalTime.of(11, 40);
        reservationService.addReservation(new AddReservationDto(memberId, toiletId, LocalDateTime.of(date, startTime),
                LocalDateTime.of(date, endTime)));

        assertThatThrownBy(() -> reservationService.addReservation(
                new AddReservationDto(memberId, toiletId, LocalDateTime.of(date, nextTime),
                        LocalDateTime.of(date, nextTimeEnd)))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 연속이어도_1시간이_지나면_예약할_수_있다() {
        Lavatory lavatory = lavatoryService.addLavatory(new Lavatory(null, Sex.MEN, "잠실굿샷남자화장실"));
        Long toiletId = toiletService.addToilet(new AddToiletDto(null, false, lavatory.getId()));
        Long memberId = memberService.addMember(new SignupDto("투다", "praisebak@naver.com", Sex.MEN));
        LocalDate date = LocalDate.now().plusDays(1L);
        LocalTime startTime = LocalTime.of(11, 0);
        LocalTime endTime = LocalTime.of(11, 30);

        LocalTime nextTime = LocalTime.of(12, 31);
        LocalTime nextTimeEnd = LocalTime.of(13, 30);
        reservationService.addReservation(new AddReservationDto(memberId, toiletId, LocalDateTime.of(date, startTime),
                LocalDateTime.of(date, endTime)));

        reservationService.addReservation(new AddReservationDto(memberId, toiletId, LocalDateTime.of(date, nextTime),
                LocalDateTime.of(date, nextTimeEnd)));

        assertThat(reservationRepository.count()).isEqualTo(2L);
    }

    @Test
    void 유효시간_이후로_화장실_예약을_할_수_없다() {
        Lavatory lavatory = lavatoryService.addLavatory(new Lavatory(null, Sex.MEN, "잠실굿샷남자화장실"));
        Long toiletId = toiletService.addToilet(new AddToiletDto(null, false, lavatory.getId()));
        Long memberId = memberService.addMember(new SignupDto("투다", "praisebak@naver.com", Sex.MEN));
        LocalDate date = LocalDate.now().plusDays(1L);
        LocalTime startTime = LocalTime.of(23, 0);
        LocalTime endTime = LocalTime.of(23, 30);

        assertThatThrownBy(() -> reservationService.addReservation(
                new AddReservationDto(memberId, toiletId, LocalDateTime.of(date, startTime),
                        LocalDateTime.of(date, endTime))))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void 유효시간_이전에_화장실_예약을_할_수_없다() {
        Lavatory lavatory = lavatoryService.addLavatory(new Lavatory(null, Sex.MEN, "잠실굿샷남자화장실"));
        Long toiletId = toiletService.addToilet(new AddToiletDto(null, false, lavatory.getId()));
        Long memberId = memberService.addMember(new SignupDto("투다", "praisebak@naver.com", Sex.MEN));
        LocalDate date = LocalDate.now().plusDays(1L);
        LocalTime startTime = LocalTime.of(5, 0);
        LocalTime endTime = LocalTime.of(6, 30);

        assertThatThrownBy(() -> reservationService.addReservation(
                new AddReservationDto(memberId, toiletId, LocalDateTime.of(date, startTime),
                        LocalDateTime.of(date, endTime))))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void 예약시_가명으로_예약된다() {
        Lavatory lavatory = lavatoryService.addLavatory(new Lavatory(null, Sex.MEN, "잠실굿샷남자화장실"));
        Long toiletId = toiletService.addToilet(new AddToiletDto(null, false, lavatory.getId()));
        Long memberId = memberService.addMember(new SignupDto("투다", "praisebak@naver.com", Sex.MEN));
        LocalDate date = LocalDate.now().plusDays(1L);
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(8, 0);

        Long reservationId = reservationService.addReservation(
                new AddReservationDto(memberId, toiletId, LocalDateTime.of(date, startTime),
                        LocalDateTime.of(date, endTime)));
        Reservation reservation = reservationRepository.findById(reservationId).get();
        String name = reservation.getMember().getName();
        String alias = reservation.getAlias();
        assertThat(name).isNotEqualTo(alias);
    }
}





