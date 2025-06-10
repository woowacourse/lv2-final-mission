package finalmission.facade.application;

import finalmission.apply.domain.Apply;
import finalmission.apply.infrastructure.ApplyRepository;
import finalmission.party.infrastructure.PartyRepository;
import finalmission.player.domain.Player;
import finalmission.player.domain.PlayerStatus;
import finalmission.player.infrastructure.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class GetApplyPartyUseCaseTest {

    @Autowired
    private GetApplyUseCase getApplyUseCase;

    @Autowired
    private CreateApplyUseCase createApplyUseCase;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private ApplyRepository applyRepository;

    // TODO 테스트하고 보니 의미가 없는 API, 개선 필요
    @Test
    void 닉네임으로_플레이어의_모든예약을_조회할수있다() {
        // given
        final Player p1 = playerRepository.save(Player.of("p1", "pw", PlayerStatus.STOP));
        final Player p2 = playerRepository.save(Player.of("p2", "pw", PlayerStatus.STOP));

        createApplyUseCase.execute(p1.getNickname());
        createApplyUseCase.execute(p2.getNickname());

        applyRepository.save(Apply.of(12L, p1.getId()));
        applyRepository.save(Apply.of(123L, p1.getId()));
        applyRepository.save(Apply.of(1234L, p1.getId()));
        applyRepository.save(Apply.of(12345L, p1.getId()));

        // when
        final List<Apply> p1Apply = getApplyUseCase.execute(p1.getNickname());
        final List<Apply> p2Apply = getApplyUseCase.execute(p2.getNickname());

        // then
        assertThat(p1Apply.size()).isEqualTo(5);
        assertThat(p2Apply.size()).isEqualTo(1);
    }
}
