package finalmission.dto.request;

import finalmission.domain.MemberRole;

public record MemberCreateRequest(
        String name,
        String email,
        String password,
        MemberRole memberRole
) {
    public MemberCreateRequest {
        if (memberRole == null) {
            memberRole = MemberRole.CUSTOMER;
        }
    }
}
