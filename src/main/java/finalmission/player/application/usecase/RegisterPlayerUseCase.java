package finalmission.player.application.usecase;

import finalmission.player.application.PlayerFinder;
import finalmission.player.application.SignInRequest;
import finalmission.player.domain.Player;
import finalmission.player.domain.PlayerStatus;
import finalmission.player.infrastructure.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterPlayerUseCase {

    private final PlayerFinder playerFinder;
    private final PlayerRepository playerRepository;
    
    public Player execute(final SignInRequest signInRequest) {
        if (playerFinder.existsByNickname(signInRequest.nickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임이야");
        }
        return playerRepository.save(
                Player.of(signInRequest.nickname(), signInRequest.password(), PlayerStatus.GO));
    }
}
