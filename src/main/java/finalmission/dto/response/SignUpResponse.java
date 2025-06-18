package finalmission.dto.response;

import finalmission.domain.MemberRole;
import finalmission.entity.Member;

public record SignUpResponse(String name, String email, MemberRole role) {

    public static SignUpResponse from(final Member saved) {
        return new SignUpResponse(saved.getName(), saved.getEmail(), saved.getRole());
    }
}
