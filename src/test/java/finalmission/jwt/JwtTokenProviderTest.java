package finalmission.jwt;

import finalmission.domain.MemberRole;
import finalmission.dto.request.CreateTokenRequest;
import finalmission.entity.Member;
import finalmission.exception.custom.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtTokenProviderTest {

    JwtTokenProvider jwtTokenProvider;

    String secretKey = "dasczvxbmjgytredscvbnhmhjggdrfsczsetrytjyhfgdssdvcbjvnfr5fdbvcx687cxbfdsfd32ddsbd45qwdsvfbgfnhjy543";
    String issuer = "test";
    Integer duration = 36000000;

    @BeforeEach
    void setup() {
        jwtTokenProvider = new JwtTokenProvider(secretKey, issuer, duration);
    }

    @Test
    @DisplayName("유저 정보를 기반으로 jwt 토큰을 생성한다.")
    void createTokenByMember() {
        //given
        Member member = new Member(1L, "test", "test@test.com", "test", MemberRole.MEMBER);
        Date date = new Date(0);
        CreateTokenRequest request = new CreateTokenRequest(member, date);

        //when
        String actual = jwtTokenProvider.createTokenByMember(request);

        //then
        String expected = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaXNzIjoidGVzdCIsImlhdCI6MCwiZXhwIjozNjAwMCwibmFtZSI6InRlc3QiLCJyb2xlIjoiTUVNQkVSIn0.UICAXV2Nse-7lhc_ypYply2TAB5L6uvehnsSB5k8PSDyFWI2S43MahieQcVBIvSJfupGoqGHUNmvdTP-wkSD0Q";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("토큰을 검증한다.")
    void extractToken() {
        //given
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + duration);

        String token = Jwts.builder()
                .setSubject("1")
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .claim("name", "test")
                .claim("role", MemberRole.MEMBER.name())
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();

        //when //then
        Assertions.assertDoesNotThrow(() -> jwtTokenProvider.extractToken(token));
    }

    @Test
    @DisplayName("만료된 토큰일 경우, 예외를 던진다.")
    void throwExceptionWhenExpired() {
        //given
        Date now = new Date(0);
        Date expireDate = new Date(now.getTime() + duration);

        String token = Jwts.builder()
                .setSubject("1")
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .claim("name", "test")
                .claim("role", MemberRole.MEMBER.name())
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();

        //when //then
        assertThatThrownBy(() -> jwtTokenProvider.extractToken(token))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("토큰이 만료되었습니다.");
    }

    @Test
    @DisplayName("같지 않은 issuer 토큰일 경우, 예외를 던진다.")
    void throwExceptionWhenNotValid() {
        //given
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + duration);

        String token = Jwts.builder()
                .setSubject("1")
                .setIssuer("notSame")
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .claim("name", "test")
                .claim("role", MemberRole.MEMBER.name())
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();

        //when //then
        assertThatThrownBy(() -> jwtTokenProvider.extractToken(token))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("유효하지 않은 토큰입니다.");
    }
}
