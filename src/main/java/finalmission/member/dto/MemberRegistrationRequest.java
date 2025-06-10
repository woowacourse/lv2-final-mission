package finalmission.member.dto;

public record MemberRegistrationRequest(String name, String email, String password, String phoneNumber) {
}