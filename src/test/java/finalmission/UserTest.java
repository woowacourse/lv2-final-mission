package finalmission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import finalmission.auth.JwtProvider;
import finalmission.user.dto.UserRequest;
import finalmission.user.dto.UserResponse;
import finalmission.user.repository.UserRepository;

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
@Sql("/data.sql")
class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    void login() throws Exception {
        // given
        UserRequest.Login request = new UserRequest.Login("meow@example.com", "1234");

        // when
        String responseBody = mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserResponse.Login response = objectMapper.readValue(responseBody, UserResponse.Login.class);

        // then
        assertThat(jwtProvider.getUserId(response.token())).isEqualTo(1);
    }

    @Test
    void join() throws Exception {
        // given
        UserRequest.Join request = new UserRequest.Join("name", "email@email.com", "password");

        // when
        mockMvc.perform(post("/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        assertThat(userRepository.findAll()).hasSize(DataConstant.USER_COUNT + 1);
    }
}
