package finalmission.infra;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtMemberTokenProviderTest {

    private final JwtMemberTokenProvider provider = new JwtMemberTokenProvider();

    @Test
    @DisplayName("사용자 ID로 이루어진 토큰을 생성한다.")
    void generateToken() {
        var member = new Member("popo", "password", "포포");

        var token = provider.generateToken(member);

        assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("사용자 ID를 토큰으로부터 추출한다.")
    void extractId() {
        var member = new Member("popo", "password", "포포");
        var token = provider.generateToken(member);

        var id = provider.extractId(token);

        assertThat(id).isEqualTo("popo");
    }
}
