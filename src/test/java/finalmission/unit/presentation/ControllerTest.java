package finalmission.unit.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.infrastructure.TokenProvider;
import finalmission.presentation.auth.AuthorizationExtractor;
import finalmission.presentation.controller.AuthController;
import finalmission.presentation.controller.MemberController;
import finalmission.presentation.controller.ReservationController;
import finalmission.presentation.controller.ToiletController;
import finalmission.service.AuthService;
import finalmission.service.MemberService;
import finalmission.service.ReservationService;
import finalmission.service.ToiletService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = {
        MemberController.class,
        AuthController.class,
        ToiletController.class,
        ReservationController.class,
        AuthorizationExtractor.class
})
@ExtendWith(value = {MockitoExtension.class})
public abstract class ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    TokenProvider tokenProvider;

    @MockitoBean
    MemberService memberService;

    @MockitoBean
    ReservationService reservationService;

    @MockitoBean
    ToiletService toiletService;

    @MockitoBean
    AuthService authService;
}
