package finalmission.auth.domain;

public interface AuthTokenProvider {

    String createAccessToken(String principal, AuthRole role);

    String getPrincipal(String token);

    AuthRole getRole(String token);

    boolean isValidToken(String token);
}
