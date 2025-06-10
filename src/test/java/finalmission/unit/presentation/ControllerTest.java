package finalmission.unit.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.presentation.MemberController;
import finalmission.service.MemberService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = {
        MemberController.class
})
@ExtendWith(value = {MockitoExtension.class})
public abstract class ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    MemberService memberService;
}
