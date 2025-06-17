package finalmission.member;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import finalmission.helper.TestHelper;
import finalmission.member.dto.request.MemberRequest;
import finalmission.member.entity.RoleType;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MemberAcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        TestHelper.signUpAsAdmin();
    }

    @Test
    @DisplayName("멤버를 생성한다.")
    void createMember() {
        // given
        var name = "테스트";
        var email = "test@email.com";
        var password = "password";
        var request = new MemberRequest(name, email, password);

        // when & then
        TestHelper.post("/members", request)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", is(name))
                .body("email", is(email))
                .body("role", is(RoleType.USER.name()));
    }

    @Test
    @DisplayName("멤버를 생성한다.")
    void createMemberAsAdmin() {
        // given
        var name = "테스트";
        var email = "test@email.com";
        var password = "password";
        var request = new MemberRequest(name, email, password);

        // when & then
        TestHelper.post("/members/admin", request)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", is(name))
                .body("email", is(email))
                .body("role", is(RoleType.ADMIN.name()));
    }

    @Test
    @DisplayName("모든 멤버를 조회한다. (어드민 권한)")
    void findAllMembers() {
        // given
        String token = TestHelper.loginAsAdmin();

        TestHelper.post("/members", new MemberRequest("테스트", "test@email.com", "password"));

        // when & then
        TestHelper.getWithToken("/members", token)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(2));
    }

    @Test
    @DisplayName("특정 멤버를 조회한다. (어드민 권한)")
    void findMemberById() {
        // given
        String token = TestHelper.loginAsAdmin();

        var name = "테스트";
        var email = "test@email.com";
        var password = "password";
        var id = TestHelper.post("/members", new MemberRequest(name, email, password))
                .then()
                .extract()
                .response()
                .jsonPath()
                .getLong("id");

        // when & then
        TestHelper.getWithToken("/members/" + id, token)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", is(name))
                .body("email", is(email))
                .body("role", is(RoleType.USER.name()));
    }

    @Test
    @DisplayName("내 정보를 조회한다.")
    void findMine() {
        // given
        String token = TestHelper.loginAsAdmin();

        // when & then
        TestHelper.getWithToken("/members/mine", token)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", is("어드민"))
                .body("email", is("admin@email.com"))
                .body("role", is(RoleType.ADMIN.name()));
    }

    @Test
    @DisplayName("어드민으로 승격시킨다. (어드민 권한)")
    void promoteToAdmin() {
        // given
        String token = TestHelper.loginAsAdmin();

        var id = TestHelper.post("/members", new MemberRequest("테스트", "test@email.com", "password"))
                .then()
                .extract()
                .response()
                .jsonPath()
                .getLong("id");

        // when
        TestHelper.patchWithToken("/members/admin?id=" + id, null, token)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // then
        TestHelper.getWithToken("/members/" + id, token)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("role", is(RoleType.ADMIN.name()));
    }

    @Test
    @DisplayName("회원을 탈퇴 처리한다. (어드민 권한)")
    void deleteMemberById() {
        // given
        String token = TestHelper.loginAsAdmin();

        var id = TestHelper.post("/members", new MemberRequest("테스트", "test@email.com", "password"))
                .then()
                .extract()
                .response()
                .jsonPath()
                .getLong("id");

        // when
        TestHelper.deleteWithToken("/members/" + id, token)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // then
        TestHelper.getWithToken("/members", token)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(1));
    }

    @Test
    @DisplayName("회원 탈퇴한다.")
    void deleteMine() {
        // given
        String token = TestHelper.loginAsAdmin();

        // when
        TestHelper.deleteWithToken("/members/mine", token)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // then
        TestHelper.getWithToken("/members", token)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(0));
    }
}
