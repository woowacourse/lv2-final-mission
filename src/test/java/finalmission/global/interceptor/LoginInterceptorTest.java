package finalmission.global.interceptor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.controller.dto.LoginRequest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest
class LoginInterceptorTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("로그인 요구 api에 로그인 없이 요청 시 예외가 발생한다")
    @Test
    void aa() throws Exception {
        // when, then
        mockMvc.perform(get("/reservations"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("로그인 후 로그인 요구 api를 요청할 수 있다")
    @Test
    void aaa() throws Exception {
        // given
        String token = login();
        Cookie cookie = new Cookie("token", token);

        // when, then
        mockMvc.perform(get("/reservations")
                        .cookie(cookie))
                .andExpect(status().isOk());
    }

    private String login() throws Exception {
        LoginRequest loginRequest = new LoginRequest("abc@naver.com", "qwer123!");
        return mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse()
                .getCookie("token")
                .getValue();
    }
}
