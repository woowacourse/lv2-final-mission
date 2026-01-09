package finalmission.auth.jwt;

import finalmission.auth.AuthToken;
import finalmission.business.model.entity.Member;

public interface JwtUtil {
    AuthToken createToken(Member member);

    Long validateAndResolveToken(final AuthToken authToken);
}
