package finalmission.infrastructure.jwt;

import finalmission.domain.Member;
import finalmission.infrastructure.Token;
import finalmission.infrastructure.TokenProvider;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements TokenProvider {

    @Override
    public Token issue(Member member) {
        return null;
    }
}
