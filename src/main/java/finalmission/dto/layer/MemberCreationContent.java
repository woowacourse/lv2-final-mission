package finalmission.dto.layer;

import finalmission.dto.request.SignupRequest;

public record MemberCreationContent(
        String email,
        String password,
        String name
) {

    public MemberCreationContent(SignupRequest request) {
        this(request.email(), request.password(), request.name());
    }
}
