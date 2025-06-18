package finalmission.controller;

import static io.restassured.RestAssured.given;

import finalmission.entity.Member;
import finalmission.jwt.JwtTokenProvider;
import finalmission.service.MemberService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RentalControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 어드민_권한으로_대여_조회() {
        // given
        Member adminMember = memberService.findById(1L);
        String adminToken = jwtTokenProvider.createTokenByMember(adminMember);

        // when & then
        given()
                .cookie("token", adminToken)
                .contentType(ContentType.JSON)
                .when()
                .get("/rentals")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 일반_사용자_권한으로_대여_조회_실패() {
        // given
        Member userMember = memberService.findById(2L);
        String userToken = jwtTokenProvider.createTokenByMember(userMember);


        // when & then
        given()
                .cookie("token", userToken)
                .contentType(ContentType.JSON)
                .when()
                .get("/rentals")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 토큰_없이_대여_조회_실패() {
        // given
        // when & then
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/rentals")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}
