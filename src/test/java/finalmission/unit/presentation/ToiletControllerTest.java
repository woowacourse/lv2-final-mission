package finalmission.unit.presentation;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import finalmission.dto.response.ToiletResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

public class ToiletControllerTest extends ControllerTest {

    @Test
    void 화장실_전체_조회에_성공한다() throws Exception {
        // given
        ToiletResponse toilet1 = new ToiletResponse(1L, "루터회관 14층 1번칸");
        List<ToiletResponse> response = List.of(toilet1);
        when(toiletService.findAllToilets()).thenReturn(response);
        // when
        ResultActions result = mockMvc.perform(get("/api/toilets"));
        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].position").value("루터회관 14층 1번칸"));
    }
}
