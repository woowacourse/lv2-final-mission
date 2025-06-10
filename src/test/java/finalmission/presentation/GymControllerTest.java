package finalmission.presentation;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import finalmission.application.GymService;
import finalmission.domain.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class GymControllerTest {

    private final GymService gymService = Mockito.mock(GymService.class);
    private final MockMvc mockMvc = MockMvcBuilders
        .standaloneSetup(new GymController(gymService))
        .build();

    @Test
    @DisplayName("헬스장을 등록하면 CREATED를 응답한다.")
    void register() throws Exception {
        mockMvc.perform(post("/gyms")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "name": "짐박스",
                    "street": "군자로 123",
                    "detail": "지하 1층"
                }
                """)
        ).andExpect(status().isCreated());
        Mockito.verify(gymService).register(eq("짐박스"), eq(new Address("군자로 123", "지하 1층")));
    }

    @ParameterizedTest
    @DisplayName("하나라도 요청 내용이 비어있으면 BAD REQUEST를 응답한다.")
    @ValueSource(strings = {
        "{ \"name\": \"\", \"street\": \"군자로 123\", \"detail\": \"지하 1층\" }",
        "{ \"name\": \"짐박스\", \"street\": \"\", \"detail\": \"지하 1층\" }",
        "{ \"name\": \"짐박스\", \"street\": \"군자로 123\", \"detail\": \"\" }",
    })
    void validateBlank(String requestJson) throws Exception {
        mockMvc.perform(post("/gyms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        ).andExpect(status().isBadRequest());
    }
}
