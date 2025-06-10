package finalmission.facade.application;

import finalmission.party.domain.Party;
import finalmission.party.domain.PartyStatus;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class CreateApplyUseCaseTest {

    @Autowired
    private CreateApplyUseCase createApplyUseCase;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Test
    void 신청하면_플레이어_상태가_GO로_변경된다() {
        // given
        final Player player = playerRepository.save(Player.of("nickname", "pw", PlayerStatus.STOP));
        final PlayerStatus playerStatusBeforeCreateApply = player.getPlayerStatus();

        // when
        createApplyUseCase.execute(player.getNickname());

        // then
        assertThat(playerStatusBeforeCreateApply).isEqualTo(PlayerStatus.STOP);

        final PlayerStatus playerStatusAfterCreateApply = player.getPlayerStatus();
        assertThat(playerStatusAfterCreateApply).isEqualTo(PlayerStatus.GO);
    }

    @Test
    void 플레이어_상태가_GO라면_신청에서_예외가_발생한다() {
        // given
        final Player player = playerRepository.save(Player.of("nickname", "pw", PlayerStatus.GO));

        // when
        // then
        assertThatThrownBy(() -> createApplyUseCase.execute(player.getNickname()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("넌 이미 진행 중이다");
    }

    @Test
    void 파티에_플레이어가_3명일떄_신청하면_파티_상태가_CLOSED로_변경된다() {
        // given
        final Player p1 = playerRepository.save(Player.of("p1", "pw", PlayerStatus.STOP));
        final Player p2 = playerRepository.save(Player.of("p2", "pw", PlayerStatus.STOP));
        final Player p3 = playerRepository.save(Player.of("p3", "pw", PlayerStatus.STOP));
        createApplyUseCase.execute(p1.getNickname());
        createApplyUseCase.execute(p2.getNickname());
        createApplyUseCase.execute(p3.getNickname());

        final List<Party> parties = partyRepository.findAll();
        assert(parties.size() == 1);
        final Party party = parties.getFirst();
        final PartyStatus partyStatusBeforeCreateApply = party.getPartyStatus();

        final Player me = playerRepository.save(Player.of("me", "pw", PlayerStatus.STOP));

        // when
        createApplyUseCase.execute(me.getNickname());

        // then
        assertThat(partyStatusBeforeCreateApply).isEqualTo(PartyStatus.OPEN);

        final PartyStatus partyStatusAfterCreateApply = party.getPartyStatus();
        assertThat(partyStatusAfterCreateApply).isEqualTo(PartyStatus.CLOSED);
    }

    @Test
    void 파티에_플레이어가_4명이라서_모집마감일때_신청하면_새로운파티가_만들어진다() {
        // given
        final Player p1 = playerRepository.save(Player.of("p1", "pw", PlayerStatus.STOP));
        final Player p2 = playerRepository.save(Player.of("p2", "pw", PlayerStatus.STOP));
        final Player p3 = playerRepository.save(Player.of("p3", "pw", PlayerStatus.STOP));
        final Player p4 = playerRepository.save(Player.of("p4", "pw", PlayerStatus.STOP));
        createApplyUseCase.execute(p1.getNickname());
        createApplyUseCase.execute(p2.getNickname());
        createApplyUseCase.execute(p3.getNickname());
        createApplyUseCase.execute(p4.getNickname());

        final int partyCountBeforeCreateApply = partyRepository.findAll().size();

        final Player me = playerRepository.save(Player.of("me", "pw", PlayerStatus.STOP));

        // when
        createApplyUseCase.execute(me.getNickname());
        // then
        assertThat(partyCountBeforeCreateApply).isEqualTo(1);

        final int partyCountAfterCreateApply = partyRepository.findAll().size();
        assertThat(partyCountAfterCreateApply).isEqualTo(2);
    }
}
