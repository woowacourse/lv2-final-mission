package finalmission.integration;

import finalmission.controller.dto.MemberSignUpRequest;
import finalmission.domain.Member;
import finalmission.domain.vo.LolName;
import finalmission.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MemberApiTest {

    @Value("${jwt.secret}")
    private String secretKey;

    private final MemberRepository memberRepository;

    @Autowired
    public MemberApiTest(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @DisplayName("회원가입을 한다.")
    @Test
    void signUp() {
        // given
        final MemberSignUpRequest request = new MemberSignUpRequest(
                new LolName("누신누황", "nunu"),
                "qwe123"
        );

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/member/signup")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("로그인을 한다.")
    @Test
    void login() {
        // given
        final Member member = new Member(
                new LolName("누신누황", "nunu"),
                "qwe123"
        );
        doSignUp(member);

        // when & then
        RestAssured.given().log().all()
                .param("playerName", "누신누황")
                .param("playerTag", "nunu")
                .param("password", "qwe123")
                .when().get("/member/login")
                .then().log().all()
                .statusCode(200)
                .cookie("token", Jwts.builder()
                        .setSubject(member.getId().toString())
                        .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                        .compact()
                );
    }

    private void doSignUp(final Member member) {
        memberRepository.save(member);
    }
}
