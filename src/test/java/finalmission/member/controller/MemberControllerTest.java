package finalmission.member.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import finalmission.fixture.MemberFixture;
import finalmission.helper.RestAssureHelper;
import finalmission.member.dto.request.MemberCreateRequest;
import finalmission.member.entity.Member;
import finalmission.member.entity.RoleType;
import finalmission.member.repository.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MemberControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("회원 생성 - 성공")
    void 회원_생성_성공() {
        // given
        Member member = MemberFixture.createDefault();
        var request = new MemberCreateRequest(member.getName(), member.getEmail(), member.getPassword());

        // when & then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)

                .when()
                .post("/members")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", is(member.getName()))
                .body("email", is(member.getEmail()))
                .body("role", is(member.getRole().getValue()));
    }

    @Test
    @DisplayName("모든 회원을 조회한다.")
    void getAllMembers() {
        // given
        List<Member> members = MemberFixture.createDefaultList(2);
        for (Member member : members) {
            var request = new MemberCreateRequest(member.getName(), member.getEmail(), member.getPassword());
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(request)

                    .when()
                    .post("/members");
        }

        Member adminMember = MemberFixture.create(RoleType.ADMIN);
        memberRepository.save(adminMember);
        String adminToken = RestAssureHelper.getLoginToken(adminMember.getEmail(), adminMember.getPassword());

        // when & then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .cookie("token", adminToken)

                .when()
                .get("/members")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(3))
                .body("[0].name", is(members.get(0).getName()))
                .body("[1].name", is(members.get(1).getName()));
    }
}
