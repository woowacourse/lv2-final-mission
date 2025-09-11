package finalmission.unit.auth.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.auth.infrastructure.JwtTokenProvider;
import finalmission.exception.auth.ExpiredTokenException;
import finalmission.member.domain.Member;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenProviderTest() {
        String secretKey = "test-secret-key-test-secret-key-test-secret-key";
        jwtTokenProvider = new JwtTokenProvider(secretKey, 1000L);
    }

    @Test
    void 토큰을_발급한다() {
        // given
        Member member = new Member(1L, "email@domain.com", "nickname", "password");
        // when
        String token = jwtTokenProvider.issue(member);
        // then
        String memberId = jwtTokenProvider.extractMemberId(token);
        assertThat(memberId).isEqualTo("1");
    }

    @Test
    void 토큰에서_회원_ID를_추출한다() {
        // given
        Member member = new Member(1L, "email@domain.com", "nickname", "password");
        String token = jwtTokenProvider.issue(member);
        // when
        String memberId = jwtTokenProvider.extractMemberId(token);
        // then
        assertThat(memberId).isEqualTo("1");
    }

    @Test
    void 만료된_토큰은_예외를_발생시킨다() {
        // given
        jwtTokenProvider = new JwtTokenProvider("test-secret-key-test-secret-key-test-secret-key", 0L);
        Member member = new Member(1L, "email@domain.com", "nickname", "password");
        String expiredToken = jwtTokenProvider.issue(member);
        // when & then
        assertThatThrownBy(() -> jwtTokenProvider.extractMemberId(expiredToken))
                .isInstanceOf(ExpiredTokenException.class);
    }
}