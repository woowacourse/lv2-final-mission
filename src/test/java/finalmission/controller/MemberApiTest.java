package finalmission.controller;

import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import finalmission.domain.Member;
import finalmission.domain.Role;
import finalmission.dto.request.SignupRequest;
import finalmission.repository.MemberRepository;
import finalmission.test.stub.RandomNameClientStub;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.RestDocumentationContextProvider;

@AutoConfigureRestDocs
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RandomNameClientStub randomNameClientStub;

    @Autowired
    private RestDocumentationContextProvider restDocumentation;

    @BeforeEach
    void setup() {
        RestAssured.filters(documentationConfiguration(restDocumentation));
    }

    @AfterEach
    void afterEach() {
        RestAssured.reset();
        memberRepository.deleteAll();
    }

    @Nested
    @DisplayName("회원가입 할 수 있다.")
    public class CanSignup {

        @DisplayName("정상적으로 회원가입 할 수 있다.")
        @Test
        void canSignup() {
            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .body(new SignupRequest("test@test.com", "qwer1234!", "kim"))
                    .filter(document("signup"))
                    .when().log().all()
                    .post("/member")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());
        }

        @DisplayName("이미 존재하는 이메일인 경우 회원가입이 불가능하다.")
        @Test
        void cannotSignupByDuplicatedEmail() {
            // given
            String duplicatedEmail = "test@test.com";
            memberRepository.save(new Member(duplicatedEmail, "qwer1234!", "kim", Role.GENERAL));

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .body(new SignupRequest(duplicatedEmail, "qwer1234!", "kim"))
                    .filter(document("signup/duplicated_email"))
                    .when().log().all()
                    .post("/member")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("이름을 입력하지 않은 경우 랜덤 이름으로 회원가입이 진행된다.")
        @Test
        void canSignupWithRandomName() {
            // given
            randomNameClientStub.setGenerateRandomNamesSuccess(List.of("random_name"));

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .body(new SignupRequest("test@test.com", "qwer1234!", null))
                    .when().log().all()
                    .post("/member")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());
        }
    }
}
