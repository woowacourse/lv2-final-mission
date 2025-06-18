package finalmission.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import finalmission.dto.request.CategoryRequest;
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
class CategoryControllerTest {

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
    void 어드민_권한으로_카테고리_등록_성공() {
        // given
        Member adminMember = memberService.findById(1L);
        String adminToken = jwtTokenProvider.createTokenByMember(adminMember);

        CategoryRequest categoryRequest = new CategoryRequest("테스트 카테고리", "테스트 설명");

        // when & then
        given()
                .cookie("token", adminToken)
                .contentType(ContentType.JSON)
                .body(categoryRequest)
                .when()
                .post("/categories")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 일반_사용자_권한으로_카테고리_등록_실패() {
        // given
        Member userMember = memberService.findById(2L);
        String userToken = jwtTokenProvider.createTokenByMember(userMember);

        CategoryRequest categoryRequest = new CategoryRequest("테스트 카테고리", "테스트 설명");

        // when & then
        given()
                .cookie("token", userToken)
                .contentType(ContentType.JSON)
                .body(categoryRequest)
                .when()
                .post("/categories")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 토큰_없이_카테고리_등록_실패() {
        // given
        CategoryRequest categoryRequest = new CategoryRequest("테스트 카테고리", "테스트 설명");

        // when & then
        given()
                .contentType(ContentType.JSON)
                .body(categoryRequest)
                .when()
                .post("/categories")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .body(equalTo("권한이 없습니다."));
    }

    @Test
    void 조회기능은_토큰_없이도_접근_가능() {
        // when & then
        given()
                .when()
                .get("/categories")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
