package finalmission.matchmaking.application;

import finalmission.mail.EmailResult;
import finalmission.mail.MailSender;
import finalmission.party.domain.Party;
import finalmission.party.domain.PartyStatus;
import finalmission.party.infrastructure.PartyRepository;
import finalmission.player.domain.Player;
import finalmission.player.domain.PlayerStatus;
import finalmission.player.infrastructure.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
class ExitMatchMakingUseCaseTest {

    @Autowired
    private ExitMatchMakingUseCase exitMatchMakingUseCase;

    @Autowired
    private JoinMatchMakingUseCase joinMatchMakingUseCase;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PartyRepository partyRepository;

    @MockitoBean
    MailSender mailSender;

    @Test
    void 신청을_취소하면_플레이어_상태가_STOP으로_변경된다() {
        // given
        final Player player = playerRepository.save(Player.of("nickname", "pw", "email", PlayerStatus.STOP));
        final PlayerStatus playerStatusBeforeCreateApply = player.getPlayerStatus();
        joinMatchMakingUseCase.execute(player.getNickname());

        final PlayerStatus playerStatusBeforeDeleteApply = player.getPlayerStatus();
        // when
        exitMatchMakingUseCase.execute(player.getNickname());

        // then
        final PlayerStatus playerStatusAfterDeleteApply = player.getPlayerStatus();
        assertThat(playerStatusBeforeCreateApply).isEqualTo(PlayerStatus.STOP);
        assertThat(playerStatusBeforeDeleteApply).isEqualTo(PlayerStatus.GO);
        assertThat(playerStatusAfterDeleteApply).isEqualTo(PlayerStatus.STOP);
    }

    @Test
    void 파티에_플레이어가_4명일때_신청을_취소하면_파티_상태가_OPEN으로_변경된다() {
        // given
        final Player p1 = playerRepository.save(Player.of("p1", "pw","email", PlayerStatus.STOP));
        final Player p2 = playerRepository.save(Player.of("p2", "pw", "email",PlayerStatus.STOP));
        final Player p3 = playerRepository.save(Player.of("p3", "pw", "email",PlayerStatus.STOP));
        final Player p4IsMe = playerRepository.save(Player.of("p4", "pw","email", PlayerStatus.STOP));
        joinMatchMakingUseCase.execute(p1.getNickname());
        joinMatchMakingUseCase.execute(p2.getNickname());
        joinMatchMakingUseCase.execute(p3.getNickname());
        joinMatchMakingUseCase.execute(p4IsMe.getNickname());

        given(mailSender.send(any(), any(), any()))
                .willReturn(new EmailResult("dummy"));


        final List<Party> parties = partyRepository.findAll();
        assert(parties.size() == 1);
        final Party party = parties.getFirst();
        final PartyStatus partyStatusBeforeDeleteApply = party.getPartyStatus();

        // when
        exitMatchMakingUseCase.execute(p4IsMe.getNickname());

        // then
        assertThat(partyStatusBeforeDeleteApply).isEqualTo(PartyStatus.CLOSED);

        final PartyStatus partyStatusAfterDeleteApply = party.getPartyStatus();
        assertThat(partyStatusAfterDeleteApply).isEqualTo(PartyStatus.OPEN);
    }
}
