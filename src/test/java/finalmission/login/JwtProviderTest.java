package finalmission.login;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.domain.login.JwtProvider;
import finalmission.domain.login.Token;
import finalmission.domain.member.Coach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;

    @DisplayName("토큰을 생성할 수 있어야 한다.")
    @Test
    void create_token() {
        Token test = jwtProvider.createToken(new Coach("test", "test@email.com"));
        assertThat(test.token()).isNotNull();
    }

}
