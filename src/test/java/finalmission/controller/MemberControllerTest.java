package finalmission.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.controller.dto.MemberLoginRequest;
import finalmission.controller.dto.MemberSignupRequest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest extends BaseCookie {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 성공적으로_회원가입_후_정보를_반환한다() throws Exception {
        // given
        MemberSignupRequest request = new MemberSignupRequest("테스터", "test@mail.com", "password",
                "01012345678");

        // when && then
        mockMvc.perform(post("/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nickname").value("테스터"));
    }

    @Test
    void 성공적으로_로그인_후_쿠키를_발급한다() throws Exception {
        // given
        MemberLoginRequest request = new MemberLoginRequest("test1@mail.com", "password");

        // when && then
        mockMvc.perform(post("/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.SET_COOKIE));
    }

    @Test
    void 로그인_사용자_정보를_반환한다() throws Exception {
        // given
        Cookie cookie = createFixtureCookie();

        // when && then
        mockMvc.perform(get("/members/check")
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nickname").value("테스트1"));
    }
}
