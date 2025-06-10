package finalmission.auth;

import static org.assertj.core.api.Assertions.assertThat;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@RequiredArgsConstructor
class JwtProviderTest {

    private final JwtProvider jwtProvider = new JwtProvider("2quf9n342980d1r98ryj39rj28dn298r92923x923nx239x23", 100000L);

    @DisplayName("JWT 토큰 발급 이후 검증 테스트")
    @Test
    void jwtTest() {
        // given
        final String phoneNumber = "01012345678";
        String token = jwtProvider.provideToken(phoneNumber);

        // when
        String extractedSubject = jwtProvider.extractSubject(token);

        // then
        assertThat(extractedSubject).isEqualTo(phoneNumber);
    }
}
