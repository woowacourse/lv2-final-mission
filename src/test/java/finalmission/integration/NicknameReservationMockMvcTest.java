package finalmission.integration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.controller.dto.LoginRequest;
import finalmission.controller.dto.ReservationCreateRequest;
import finalmission.controller.dto.ReservationUpdateRequest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest
public class NicknameReservationMockMvcTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Cookie loginCookie;

    @BeforeEach
    void setUp() throws Exception {
        loginCookie = new Cookie("token", login());
    }

    @DisplayName("예약을 생성할 수 있다")
    @Test
    void create() throws Exception {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest("레오오");

        // when, then
        mockMvc.perform(post("/reservations")
                        .cookie(loginCookie)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name").value("레오오"));
    }

    @DisplayName("예약 목록을 조회할 수 있다")
    @Test
    void aaa() throws Exception {
        // given
        create();

        // when, then
        mockMvc.perform(get("/reservations")
                        .cookie(loginCookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("레오"))
                .andExpect(jsonPath("$[0].status").value("예약"))
                .andExpect(jsonPath("$[1].name").value("레오오"))
                .andExpect(jsonPath("$[1].status").value("예약"));
    }

    @DisplayName("예약을 삭제할 수 있다")
    @Test
    void aaaa() throws Exception {
        // when
        mockMvc.perform(delete("/reservations/1")
                        .cookie(loginCookie))
                .andExpect(status().isNoContent());

        // then
        mockMvc.perform(get("/reservations")
                        .cookie(loginCookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @DisplayName("예약 닉네임을 수정할 수 있다")
    @Test
    void aaaaa() throws Exception {
        // when
        ReservationUpdateRequest request = new ReservationUpdateRequest("레오오");
        mockMvc.perform(patch("/reservations/1")
                        .cookie(loginCookie)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        // then
        mockMvc.perform(get("/reservations")
                        .cookie(loginCookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("레오오"))
                .andExpect(jsonPath("$[0].status").value("예약"));
    }

    @DisplayName("예약을 확정할 수 있다")
    @Test
    void aaaaaa() throws Exception {
        // when
        mockMvc.perform(post("/reservations/1/confirm")
                        .cookie(loginCookie))
                .andExpect(status().isNoContent());

        // then
        mockMvc.perform(get("/reservations")
                        .cookie(loginCookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("레오"))
                .andExpect(jsonPath("$[0].status").value("확정"));
    }

    private String login() throws Exception {
        LoginRequest loginRequest = new LoginRequest("abc@naver.com", "qwer123!");
        return mockMvc.perform(post("/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse()
                .getCookie("token")
                .getValue();
    }
}
