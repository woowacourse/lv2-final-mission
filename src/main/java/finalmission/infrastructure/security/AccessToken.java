package finalmission.infrastructure.security;

import io.jsonwebtoken.Jwts;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;

public record AccessToken(
        String value
) {

    public static AccessToken create(TokenIssueRequest tokenIssueRequest) {
        return new AccessToken(Jwts.builder()
                .issuedAt(Date.from(Instant.now()))
                .subject(tokenIssueRequest.memberId().toString())
                .signWith(tokenIssueRequest.secretKey())
                .compact()
        );
    }

    public static AccessToken of(String value) {
        return new AccessToken(value);
    }

    public Long extractMemberId(SecretKey secretKey) {
        return Long.valueOf(Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(value)
                .getPayload()
                .getSubject()
        );
    }
}
