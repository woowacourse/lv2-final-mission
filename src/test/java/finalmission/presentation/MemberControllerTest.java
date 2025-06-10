package finalmission.presentation;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import finalmission.application.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class MemberControllerTest {

    private final MemberService memberService = Mockito.mock(MemberService.class);
    private final MockMvc mockMvc = MockMvcBuilders
        .standaloneSetup(new MemberController(memberService))
        .build();

    @Test
    @DisplayName("사용자를 등록하면 CREATED를 응답한다.")
    void register() throws Exception {
        mockMvc.perform(post("/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "id": "popo",
                    "password": "password",
                    "name": "포포"
                }
                """)
        ).andExpect(status().isCreated());
        Mockito.verify(memberService).register(eq("popo"), eq("password"), eq("포포"));
    }

    @ParameterizedTest
    @DisplayName("요청 내용이 비어있으면 BAD REQUEST를 응답한다.")
    @ValueSource(strings = {
        "{ \"id\": \"\", \"password\": \"password\", \"name\": \"포포\" }",
        "{ \"id\": \"popo\", \"password\": \"\", \"name\": \"포포\" }",
        "{ \"id\": \"popo\", \"password\": \"password\", \"name\": \"\" }",
    })
    void validateBlank(String requestJson) throws Exception {
        mockMvc.perform(post("/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        ).andExpect(status().isBadRequest());
    }
}
