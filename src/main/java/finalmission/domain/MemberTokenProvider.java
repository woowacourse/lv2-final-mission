package finalmission.domain;

public interface MemberTokenProvider {

    String generateToken(AuthInfo authInfo);

    String extractId(String token);
}
