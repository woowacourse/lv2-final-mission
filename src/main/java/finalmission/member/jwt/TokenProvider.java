package finalmission.member.jwt;


public interface TokenProvider {

    String createToken(TokenCreateRequest request);

    Long getMemberIdFromToken(String token);
}
