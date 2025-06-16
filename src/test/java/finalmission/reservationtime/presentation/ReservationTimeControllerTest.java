package finalmission.reservationtime.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import finalmission.common.JwtTokenProvider;
import finalmission.common.LoginArgumentResolver;
import finalmission.common.TokenCookieManager;
import finalmission.member.service.LoginService;
import finalmission.reservationtime.dto.ReservationTimeRequest;
import finalmission.reservationtime.dto.ReservationTimeResponse;
import finalmission.reservationtime.service.ReservationTimeService;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;

@WebMvcTest(ReservationTimeController.class)
@Import({LoginArgumentResolver.class})
class ReservationTimeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationTimeService reservationTimeService;

    @MockitoBean
    private LoginService loginService;

    @MockitoBean
    private TokenCookieManager tokenCookieManager;

    @MockitoBean
    private JwtTokenProvider jwtTokenContainer;

    @Test
    void 예약_시간_생성에_성공한다() throws Exception {
        // given
        ReservationTimeRequest request = new ReservationTimeRequest(LocalTime.of(10, 0));
        ReservationTimeResponse response = new ReservationTimeResponse(1L, LocalTime.of(10, 0));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        when(reservationTimeService.save(any()))
                .thenReturn(response);

        // when & then
        String jsonContent = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
