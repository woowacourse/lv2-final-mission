package finalmission.dto;

import finalmission.domain.TokenAuthRole;

public record AuthenticatedMember(
        TokenAuthRole tokenAuthRole,
        Long id
) {

}
