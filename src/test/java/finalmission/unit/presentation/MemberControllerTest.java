package finalmission.unit.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import finalmission.dto.MemberRequest;
import finalmission.dto.MemberResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

public class MemberControllerTest extends ControllerTest {

    @Test
    void 회원가입에_성공한다() throws Exception {
        // given
        MemberRequest request = new MemberRequest("email1@domain.com", "이름1", "1234");
        MemberResponse response = new MemberResponse(1L, "email1@domain.com", "이름1");
        when(memberService.createMember(any(MemberRequest.class))).thenReturn(response);
        // when
        ResultActions result = mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("email1@domain.com"))
                .andExpect(jsonPath("$.name").value("이름1"));
    }
}
