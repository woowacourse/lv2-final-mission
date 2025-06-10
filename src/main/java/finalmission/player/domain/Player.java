package finalmission.player.domain;

import finalmission.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Player extends BaseEntity {

    private String nickname;
    private String password;

    private PlayerStatus playerStatus;

    public static Player of(final String nickname, final String password, final PlayerStatus playerStatus) {
        return new Player(nickname, password, playerStatus);
    }

    public void go() {
        requireStop();
        playerStatus = PlayerStatus.GO;
    }

    public void stop() {
        requirdGo();
        playerStatus = PlayerStatus.STOP;
    }

    private void requireStop() {
        if (playerStatus.isStop()) {
            return;
        }
        throw new IllegalStateException("넌 이미 진행 중이다");
    }

    private void requirdGo() {
        if (playerStatus.isGo()) {
            return;
        }
        throw new IllegalStateException("넌 이미 진행 중이다");
    }
}
