package finalmission.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;

    public AccessToken issue(Long memberId) {
        TokenIssueRequest tokenIssueRequest = getTokenIssueRequest(memberId);
        return AccessToken.create(tokenIssueRequest);
    }

    private TokenIssueRequest getTokenIssueRequest(Long memberId) {
        return new TokenIssueRequest(memberId, jwtProperties.secretKey());
    }

    public Long extractMemberId(AccessToken accessToken) {
        return accessToken.extractMemberId(jwtProperties.secretKey());
    }
}
