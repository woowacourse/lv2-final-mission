package finalmission.player.application.usecase;

import finalmission.matchmaking.ui.UserInfo;
import finalmission.player.application.PlayerFinder;
import finalmission.player.domain.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidatePlayerUseCase {

    private final PlayerFinder playerFinder;

    public void execute(final UserInfo userInfo) {
        final Player player = playerFinder.getByNickname(userInfo.nickname());
        if (player.getPassword().equals(userInfo.password())) {
            return;
        }

        throw new IllegalArgumentException("비밀번호가 틀렸습니다");
    }
}
