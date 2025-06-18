package finalmission.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import finalmission.domain.Member;
import finalmission.domain.Nickname;
import finalmission.domain.NicknameReservation;
import finalmission.fake.FakeMemberRepository;
import finalmission.fake.FakeNicknameReservationRepository;
import finalmission.repository.MemberRepository;
import finalmission.repository.NicknameReservationRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NicknameReservationServiceTest {

    private MemberRepository fakeMemberRepository;
    private NicknameReservationRepository fakeNicknameReservationRepository;
    private NicknameReservationService nicknameReservationService;
    private Member savedMember;
    private NicknameReservation savedReservation;

    @BeforeEach
    void setUp() {
        fakeMemberRepository = new FakeMemberRepository();
        fakeNicknameReservationRepository = new FakeNicknameReservationRepository();
        nicknameReservationService = new NicknameReservationService(fakeNicknameReservationRepository,
                fakeMemberRepository);
        savedMember = fakeMemberRepository.save(new Member());
        savedReservation = nicknameReservationService.reserve("레오", savedMember.getId());
    }

    @DisplayName("예약할 수 있다")
    @Test
    void aa() {
        // when
        NicknameReservation result = nicknameReservationService.reserve("레오레", savedMember.getId());

        // then
        assertAll(
                () -> assertThat(result.getId()).isNotNull(),
                () -> assertThat(result.getMember().getId()).isEqualTo(savedMember.getId()),
                () -> assertThat(result.getNickname().getName()).isEqualTo("레오레")
        );
    }

    @DisplayName("한 크루가 두 개 이상 예약 시 예외가 발생한다")
    @Test
    void aaa() {
        // when
        nicknameReservationService.reserve("레오레", savedMember.getId());

        // then
        assertThatThrownBy(() -> nicknameReservationService.reserve("레오레오", savedMember.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임 예약은 최대 2개까지만 가능합니다.");
    }

    @DisplayName("닉네임이 중복될 시 예외가 발생한다")
    @Test
    void aaaa() {
        // then
        assertThatThrownBy(() -> nicknameReservationService.reserve("레오", savedMember.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 예약 중인 닉네임입니다.");
    }

    @DisplayName("닉네임을 확정할 경우 다른 닉네임을 생성할 수 없다")
    @Test
    void aaaab() {
        // when
        nicknameReservationService.confirm(savedReservation.getId(), savedMember.getId());

        // then
        assertThatThrownBy(() -> nicknameReservationService.reserve("레오레", savedMember.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 확정된 닉네임이 있습니다.");
    }

    @DisplayName("예약한 닉네임을 수정할 수 있다")
    @Test
    void aab() {
        // when
        nicknameReservationService.update("레오레", savedReservation.getId(), savedMember.getId());

        // then
        assertThat(savedReservation.getNickname()).isEqualTo(new Nickname("레오레"));
    }

    @DisplayName("자신의 것이 아닌 예약을 수정할 경우 예외가 발생한다")
    @Test
    void aaab() {
        // given
        long otherMemberId = savedMember.getId() + 1;

        // when, then
        assertThatThrownBy(() -> nicknameReservationService.update("레오레", savedReservation.getId(), otherMemberId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 예약에 대한 권한이 없습니다.");
    }

    @DisplayName("수정하려는 닉네임이 다른 닉네임과 중복될 경우 예외가 발생한다")
    @Test
    void aaabb() {
        // given
        NicknameReservation reservation = nicknameReservationService.reserve("레오레", savedMember.getId());

        // when, then
        assertThatThrownBy(() -> nicknameReservationService.update("레오", reservation.getId(), savedMember.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 예약 중인 닉네임입니다.");
    }

    @DisplayName("예약을 취소할 수 있다")
    @Test
    void aaaaa() {
        // when
        nicknameReservationService.cancel(savedReservation.getId(), savedMember.getId());

        // then
        List<NicknameReservation> reservations = fakeNicknameReservationRepository.findAllByMember(savedMember);
        assertThat(reservations).isEmpty();
    }

    @DisplayName("자신의 것이 아닌 예약을 취소할 경우 예외가 발생한다")
    @Test
    void aaaaaa() {
        // given
        long otherMemberId = savedMember.getId() + 1;

        // when, then
        assertThatThrownBy(() -> nicknameReservationService.cancel(savedReservation.getId(), otherMemberId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 예약에 대한 권한이 없습니다.");
    }

    @DisplayName("자신의 것이 아닌 예약을 취소할 경우 예외가 발생한다")
    @Test
    void aaaaaaa() {
        // given
        savedReservation.confirm();

        // when, then
        assertThatThrownBy(() -> nicknameReservationService.cancel(savedReservation.getId(), savedMember.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("확정된 예약은 취소할 수 없습니다.");
    }

    @DisplayName("예약을 확정하면 다른 예약은 취소된다")
    @Test
    void aaaaaaaa() {
        // given
        nicknameReservationService.reserve("레오레", savedMember.getId());

        // when
        nicknameReservationService.confirm(savedReservation.getId(), savedMember.getId());

        // then
        List<NicknameReservation> reservations = fakeNicknameReservationRepository.findAllByMember(savedMember);
        assertThat(reservations).containsExactly(savedReservation);
    }

    @DisplayName("자신의 것이 아닌 예약을 확정할 경우 예외가 발생한다")
    @Test
    void aaaaaaaaa() {
        // given
        long otherMemberId = savedMember.getId() + 1;

        // when, then
        assertThatThrownBy(() -> nicknameReservationService.confirm(savedReservation.getId(), otherMemberId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 예약에 대한 권한이 없습니다.");
    }
}
