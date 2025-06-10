package finalmission.member.dto;

public record LoginMemberRequest(
        String email,
        String password
) {
}
