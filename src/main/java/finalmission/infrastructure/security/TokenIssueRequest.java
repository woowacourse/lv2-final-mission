package finalmission.infrastructure.security;

import javax.crypto.SecretKey;

public record TokenIssueRequest(
        Long memberId,
        SecretKey secretKey
) {
}
