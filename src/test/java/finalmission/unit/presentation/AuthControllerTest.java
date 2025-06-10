package finalmission.unit.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import finalmission.dto.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

public class AuthControllerTest extends ControllerTest {

    @Test
    void 로그인에_성공하고_쿠키를_설정한다() throws Exception {
        // given
        LoginRequest request = new LoginRequest("email1@domain.com", "1234");
        when(authService.login(any(LoginRequest.class))).thenReturn("accessToken");
        // when
        ResultActions result = mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        // then
        result.andExpect(status().isOk())
                .andExpect(cookie().value("token", "accessToken"));
    }
}
