package finalmission.running.service;

import finalmission.member.domain.Member;
import finalmission.member.domain.Role;
import finalmission.member.repository.MemberRepository;
import finalmission.running.domain.Participant;
import finalmission.running.domain.RunningSession;
import finalmission.running.exception.ReservationException;
import finalmission.running.repository.RunningReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ParticipantServiceTest {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RunningReservationRepository reservationRepository;

    private Member creator;
    private RunningSession runningSession;

    @BeforeEach
    void setUp() {
        creator = memberRepository.save(Member.createWithoutId("드라고", "1", "1234", Role.USER));
        runningSession = reservationRepository.save(
            RunningSession.createWithoutId(
                creator, LocalDate.now().plusDays(1),
                LocalTime.of(10, 0), LocalTime.of(11, 0)));
    }

    @Test
    void 세션에_참여할_수_있다() {
        // given
        Member joinner = memberRepository.save(Member.createWithoutId("참가자", "2", "1234", Role.USER));

        // when
        Participant participant = participantService.createParticipant(Participant.createWithoutId(runningSession, joinner));

        // then
        assertThat(participant.getId()).isEqualTo(1L);
    }

    @Test
    void 생성자는_참가자가_될_수_없다() {
        // when & then
        assertThatThrownBy(() -> participantService.createParticipant(Participant.createWithoutId(runningSession, creator)))
            .isInstanceOf(ReservationException.class)
            .hasMessage("세션 생성자는 참가자가 될 수 없습니다.");
    }
}