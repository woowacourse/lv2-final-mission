package finalmission.general.auth.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseCookie;

class CookieManagerTest {

    CookieManager cookieManager = new CookieManager();

    @Test
    void 주어진_액세스_토큰으로_쿠키를_생성할_수_있다() {
        // Given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLtlITrpqwiLCJleHAiOjE3NTAxNTM3NDV9.bDb6JgQCnJ1t6gfNe1d8iWzwQZ-ukkIL88zrXdc_mvo";

        // When
        ResponseCookie cookie = cookieManager.generateJwtCookie(token);

        // Then
        Assertions.assertThat(cookie.toString())
                .contains("token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLtlITrpqwiLCJleHAiOjE3NTAxNTM3NDV9.bDb6JgQCnJ1t6gfNe1d8iWzwQZ-ukkIL88zrXdc_mvo;",
                        "Max-Age=3600;");
    }
}