package finalmission.matchmaking.application;

import finalmission.apply.application.ApplyCounter;
import finalmission.apply.application.ApplyCreator;
import finalmission.apply.application.ApplyFinder;
import finalmission.apply.domain.Apply;
import finalmission.mail.MailSender;
import finalmission.party.application.PartyStatusUpdater;
import finalmission.party.application.VacancyGetter;
import finalmission.player.application.PlayerFinder;
import finalmission.player.application.PlayerStatusUpdater;
import finalmission.player.domain.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinMatchMakingUseCase {

    private final PlayerFinder playerFinder;
    private final VacancyGetter vacancyGetter;
    private final ApplyCreator applyCreator;
    private final PlayerStatusUpdater playerStatusUpdater;
    private final PartyStatusUpdater partyStatusUpdater;
    private final ApplyCounter applyCounter;
    private final ApplyFinder applyFinder;
    private final MailSender mailSender;

    public void execute(final String nickname) {
        final Long playerId = playerFinder.getIdByNickname(nickname);

        final Long partyId = vacancyGetter.execute();
        applyCreator.execute(partyId, playerId);
        playerStatusUpdater.go(playerId);

        final int count = applyCounter.executeByPartyId(partyId);
        if (count == 4) {
            partyStatusUpdater.close(partyId);

            final List<Long> partyPlayerIds = applyFinder.getAllByPartyId(partyId).stream()
                    .map(Apply::getPlayerId)
                    .toList();

            final List<Player> partyPlayers = playerFinder.getAllByIds(partyPlayerIds);

            final String nicknames = partyPlayers.stream()
                    .map(Player::getNickname)
                    .collect(Collectors.joining(", "));

            for (final Player player : partyPlayers) {
                mailSender.send(
                        player.getEmail(),
                        "4명 모집 완료",
                        "닉네임: %s".formatted(nicknames));

                System.out.println("발사");
            }
        }

        if (count > 5) {
            throw new IllegalStateException("있을 수 없는 일이 일어난거야");
        }
    }
}
