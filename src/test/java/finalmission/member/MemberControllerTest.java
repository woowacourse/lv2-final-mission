package finalmission.member;

import static org.assertj.core.api.Assertions.assertThat;
import finalmission.member.domain.Member;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberControllerTest {

    private final int port;
    private final JdbcTemplate jdbcTemplate;

    public MemberControllerTest(
            @LocalServerPort final int port,
            @Autowired final JdbcTemplate jdbcTemplate
    ) {
        this.port = port;
        this.jdbcTemplate = jdbcTemplate;
    }

    @DisplayName("유저 추가 테스트")
    @Test
    void createMemberTest() {
        // given
        String phoneNumber = "01012345678";

        // when
        RestAssured
                .given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(phoneNumber)

                .when()
                .post("/member/signup")

                .then().log().all()
                .statusCode(201);

        // then
        final String sql = "SELECT phone_number FROM Member";
        Member member = jdbcTemplate.queryForObject(sql, Member.class);

        assertThat(member.getPhoneNumber()).isEqualTo(phoneNumber);
    }
}
