package finalmission.general.auth.util;

import finalmission.general.auth.util.JwtProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtProviderTest {

    JwtProvider jwtProvider = new JwtProvider();

    @Test
    void 토큰을_생성할_수_있다() {
        // Given
        String username = "username";

        // When & Then
        assertThat(jwtProvider.generateToken(username)).startsWith("ey");
    }

    @Test
    void 토큰에서_아이디를_추출할_수_있다() {
        // Given
        String username = "username";
        String token = jwtProvider.generateToken(username);

        // When & Then
        assertThat(jwtProvider.getUsername(token)).isEqualTo(username);
    }
}