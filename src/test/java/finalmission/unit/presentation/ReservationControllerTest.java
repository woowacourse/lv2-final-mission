package finalmission.unit.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import finalmission.dto.request.ReservationRequest;
import finalmission.dto.response.MemberResponse;
import finalmission.dto.response.ReservationResponse;
import finalmission.dto.response.ToiletResponse;
import jakarta.servlet.http.Cookie;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

public class ReservationControllerTest extends ControllerTest {

    @Test
    void 예약을_생성한다() throws Exception {
        // given
        ReservationRequest request = new ReservationRequest(1L, LocalDate.of(2025, 1, 1), LocalTime.of(9, 0),
                LocalTime.of(10, 0));
        MemberResponse member = new MemberResponse(1L, "email1@domain.com", "name1");
        ToiletResponse toilet = new ToiletResponse(1L, "루터회관 14층 1번");
        ReservationResponse response = new ReservationResponse(1L, LocalDate.of(2025, 1, 1), LocalTime.of(9, 0),
                LocalTime.of(10, 0), member, toilet);
        when(reservationService.createReservation(any(), any())).thenReturn(response);
        when(tokenProvider.extractMemberId("accessToken")).thenReturn("1");
        // when
        ResultActions result = mockMvc.perform(post("/api/reservations")
                .cookie(new Cookie("token", "accessToken"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.date").value("2025-01-01"));
    }
}
