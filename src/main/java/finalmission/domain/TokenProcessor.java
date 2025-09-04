package finalmission.domain;

import finalmission.dto.AuthenticatedMember;

public interface TokenProcessor {

    String createToken(TokenAuthRole tokenAuthRole, Long id);

    AuthenticatedMember extract(String rawToken);
}
