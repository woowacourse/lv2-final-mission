package finalmission.reservation.controller;

import finalmission.auth.JwtTokenProvider;
import finalmission.auth.LoginMemberInterceptor;
import finalmission.reservation.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationService reservationService;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @MockitoBean
    private LoginMemberInterceptor loginMemberInterceptor;

    @Test
    void createReservation() {
        when(jwtTokenProvider.extractId(any()))
                .thenReturn(1L);

    }
}