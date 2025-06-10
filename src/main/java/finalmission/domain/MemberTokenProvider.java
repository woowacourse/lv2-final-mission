package finalmission.domain;

public interface MemberTokenProvider {

    String generateToken(Member member);

    String extractId(String token);
}
