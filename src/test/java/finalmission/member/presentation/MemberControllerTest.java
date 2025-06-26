package finalmission.member.presentation;

import finalmission.member.presentation.dto.request.MemberCreateWebRequest;
import finalmission.member.presentation.dto.request.MemberLoginWebRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MemberControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 회원가입_요청을_보내고_성공_시_201을_반환한다() {
        String username = "username";
        String password = "password";
        String name = "프리";

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new MemberCreateWebRequest(username, password, name))
                .when()
                    .post("/members/signUp")
                .then()
                    .statusCode(201)
                    .body(
                            "username", equalTo(username),
                            "name", equalTo(name)
                    );
    }

    @Test
    void 동일한_아이디로_회원가입_요청_시_회원가입을_처리할_수_없다() {
        String username = "username";

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new MemberCreateWebRequest(username, "password", "프리"))
                .when()
                    .post("/members/signUp")
                .then()
                    .statusCode(201);

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new MemberCreateWebRequest(username, "password", "프리"))
                .when()
                    .post("/members/signUp")
                .then()
                    .statusCode(500);
    }

    @Test
    void 로그인을_요청하고_성공_시_200을_응답한다() {
        String username = "username";
        String password = "password";

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new MemberCreateWebRequest(username, password, "프리"))
                .when()
                    .post("/members/signUp")
                .then()
                    .statusCode(201);

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new MemberLoginWebRequest(username, password))
                .when()
                    .post("/members/login")
                .then()
                    .statusCode(200)
                    .cookie("token", startsWith("ey"));
    }

    @Test
    void 존재하지_않는_아이디로_요청_시_로그인을_처리할_수_없다() {
        String username = "username";
        String password = "password";

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new MemberCreateWebRequest(username, password, "프리"))
                .when()
                .post("/members/signUp")
                .then()
                .statusCode(201);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new MemberLoginWebRequest("invalidUsername", password))
                .when()
                .post("/members/login")
                .then()
                .statusCode(500);
    }

    @Test
    void 틀린_비밀번호로_요청_시_로그인을_처리할_수_없다() {
        String username = "username";
        String password = "password";

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new MemberCreateWebRequest(username, password, "프리"))
                .when()
                .post("/members/signUp")
                .then()
                .statusCode(201);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new MemberLoginWebRequest(username, "invalidPassword"))
                .when()
                .post("/members/login")
                .then()
                .statusCode(500);
    }
}