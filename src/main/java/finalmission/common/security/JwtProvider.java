package finalmission.common.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.IncorrectClaimException;
import com.auth0.jwt.exceptions.MissingClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import finalmission.common.config.JwtProperties;
import finalmission.member.domain.Member;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(JwtProperties.class)
public class JwtProvider {

    private final JwtProperties jwtProperties;

    public JwtProvider(final JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String createJwtToken(final Member member) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validity = now.plusMinutes(jwtProperties.getExpireLength() / 1000 / 60);
        return JWT.create()
                .withSubject(member.getId().toString())
                .withClaim("nickname", member.getNickname())
                .withClaim("role", member.getMemberRole().name())
                .withIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(validity.atZone(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }

    public boolean validateJwtToken(final String token) {
        try {
            DecodedJWT decoded = verifyToken(token);
            return decoded.getExpiresAt().before(new Date());
        } catch (AlgorithmMismatchException | SignatureVerificationException | TokenExpiredException |
                 MissingClaimException | IncorrectClaimException e) {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
    }

    public String getSubject(final String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getSubject();
    }

    public String getClaim(final String token, final String name) {
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getClaim(name).asString();
    }

    private DecodedJWT verifyToken(final String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey()))
                .build();
        return verifier.verify(token);
    }
}
