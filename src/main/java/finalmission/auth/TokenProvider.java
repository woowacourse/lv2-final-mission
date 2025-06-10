package finalmission.auth;

public interface TokenProvider {

    String createToken(String payload);

    String parsePayload(String token);
}
