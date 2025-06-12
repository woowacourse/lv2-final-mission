package finalmission.member.auth;


import finalmission.member.domain.Role;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
public class JwtTokenProvider {

    private final String secretKey;
    private final int expirationTime;

    public JwtTokenProvider(
        @Value("${secret.key}")String secretKey,
        @Value("${expiration.time}") int expirationTime) {
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
    }

    public String createToken(Long id, Role role) {
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");

        return Jwts.builder()
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .setSubject(id.toString())
            .claim("role", role)
            .setIssuedAt(new Date())
            .signWith(key, HS256)
            .compact();
    }
}
