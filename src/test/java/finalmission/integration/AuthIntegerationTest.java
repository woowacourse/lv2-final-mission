package finalmission.integration;

import static finalmission.helper.AuthTokenExtractor.extractMemberToken;
import static finalmission.helper.DocsFilterFactory.createDocumentFilter;
import static finalmission.helper.RestAssuredRequestUtils.sendGetWithTokenAndFilter;
import static finalmission.helper.RestAssuredRequestUtils.sendPostWithFilter;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import io.restassured.filter.Filter;
import java.util.Map;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

public class AuthIntegerationTest extends IntegrationTest {

    private static final String DOCS_BASE_DIR = "auth";

    @Nested
    @DisplayName("인증 API")
    class AdminLoginApi {

        @Test
        @DisplayName("로그인 API")
        void login() {
            Map<String, String> body = Map.of(
                    "email", "member1@email.com",
                    "password", "password"
            );

            Filter filter = createDocumentFilter(DOCS_BASE_DIR, "login",
                    requestFields(
                            fieldWithPath("email").description("멤버 이메일"),
                            fieldWithPath("password").description("멤버 비밀번호")
                    ));

            sendPostWithFilter("/auth/login", body, spec, filter)
                    .then().statusCode(200)
                    .header(HttpHeaders.SET_COOKIE, Matchers.containsString("token="));
        }

        @Test
        @DisplayName("로그인 확인 API")
        void checkLogin() {
            String token = extractMemberToken();

            Filter filter = createDocumentFilter(DOCS_BASE_DIR, "check",
                    responseFields(fieldWithPath("name").description("멤버 이름")));

            sendGetWithTokenAndFilter("/auth/login/check", spec, token, filter)
                    .then().statusCode(200);
        }
    }
}
