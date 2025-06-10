package finalmission.infra;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.domain.AuthInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtMemberTokenProviderTest {

    private final JwtMemberTokenProvider provider = new JwtMemberTokenProvider();

    @Test
    @DisplayName("사용자 ID로 이루어진 토큰을 생성한다.")
    void generateToken() {
        var authInfo = new AuthInfo("popoId");

        var token = provider.generateToken(authInfo);

        assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("사용자 ID를 토큰으로부터 추출한다.")
    void extractId() {
        var authInfo = new AuthInfo("popoId");
        var token = provider.generateToken(authInfo);

        var id = provider.extractId(token);

        assertThat(id).isEqualTo("popoId");
    }
}
