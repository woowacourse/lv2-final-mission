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

@SpringBootTest
@Transactional
class DeleteApplyUseCaseTest {

    @Autowired
    private DeleteApplyUseCase deleteApplyUseCase;

    @Autowired
    private CreateApplyUseCase createApplyUseCase;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Test
    void 신청을_취소하면_플레이어_상태가_STOP으로_변경된다() {
        // given
        final Player player = playerRepository.save(Player.of("nickname", "pw", PlayerStatus.STOP));
        final PlayerStatus playerStatusBeforeCreateApply = player.getPlayerStatus();
        createApplyUseCase.execute(player.getNickname());

        final PlayerStatus playerStatusBeforeDeleteApply = player.getPlayerStatus();
        // when
        deleteApplyUseCase.execute(player.getNickname());

        // then
        final PlayerStatus playerStatusAfterDeleteApply = player.getPlayerStatus();
        assertThat(playerStatusBeforeCreateApply).isEqualTo(PlayerStatus.STOP);
        assertThat(playerStatusBeforeDeleteApply).isEqualTo(PlayerStatus.GO);
        assertThat(playerStatusAfterDeleteApply).isEqualTo(PlayerStatus.STOP);
    }

    @Test
    void 파티에_플레이어가_4명일때_신청을_취소하면_파티_상태가_OPEN으로_변경된다() {
        // given
        final Player p1 = playerRepository.save(Player.of("p1", "pw", PlayerStatus.STOP));
        final Player p2 = playerRepository.save(Player.of("p2", "pw", PlayerStatus.STOP));
        final Player p3 = playerRepository.save(Player.of("p3", "pw", PlayerStatus.STOP));
        final Player p4IsMe = playerRepository.save(Player.of("p4", "pw", PlayerStatus.STOP));
        createApplyUseCase.execute(p1.getNickname());
        createApplyUseCase.execute(p2.getNickname());
        createApplyUseCase.execute(p3.getNickname());
        createApplyUseCase.execute(p4IsMe.getNickname());

        final List<Party> parties = partyRepository.findAll();
        assert(parties.size() == 1);
        final Party party = parties.getFirst();
        final PartyStatus partyStatusBeforeDeleteApply = party.getPartyStatus();

        // when
        deleteApplyUseCase.execute(p4IsMe.getNickname());

        // then
        assertThat(partyStatusBeforeDeleteApply).isEqualTo(PartyStatus.CLOSED);

        final PartyStatus partyStatusAfterDeleteApply = party.getPartyStatus();
        assertThat(partyStatusAfterDeleteApply).isEqualTo(PartyStatus.OPEN);
    }
}
