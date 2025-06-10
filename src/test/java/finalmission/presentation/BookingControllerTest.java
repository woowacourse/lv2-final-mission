package finalmission.presentation;

import static finalmission.TestFixtures.anyBooking;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import finalmission.TestFixtures;
import finalmission.application.BookingService;
import finalmission.domain.AuthenticationException;
import finalmission.domain.MemberTokenProvider;
import jakarta.servlet.http.Cookie;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class BookingControllerTest {

    private final BookingService bookingService = Mockito.mock(BookingService.class);
    private final MemberTokenProvider tokenProvider = Mockito.mock(MemberTokenProvider.class);
    private final MockMvc mockMvc = MockMvcBuilders
        .standaloneSetup(new BookingController(bookingService, tokenProvider))
        .build();

    @Test
    @DisplayName("예약을 하면 CREATED를 응답한다.")
    void book() throws Exception {
        Mockito.doReturn("popo").when(tokenProvider).extractId(eq("token"));

        mockMvc.perform(post("/bookings")
            .cookie(new Cookie("token", "token"))
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "gymId": "3bfc19bd-23df-482e-8857-3d4d3a173482",
                    "date": "2025-06-17"
                }
                """)
        ).andExpect(status().isCreated());

        Mockito.verify(bookingService).book(
            eq("popo"),
            eq(UUID.fromString("3bfc19bd-23df-482e-8857-3d4d3a173482")),
            eq(LocalDate.of(2025, 6, 17))
        );
    }

    @Test
    @DisplayName("로그인하지 않고 예약하려 하면 UNAUTHORIZED를 응답한다.")
    void bookShouldLogin() throws Exception {
        Mockito.doThrow(AuthenticationException.class).when(tokenProvider).extractId(any());

        mockMvc.perform(post("/bookings")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "gymId": "3bfc19bd-23df-482e-8857-3d4d3a173482",
                    "date": "2025-06-17"
                }
                """)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("내 예약을 조회하면 OK와 함께 예약들로 이루어진 컬렉션을 응답한다.")
    void getMyBookings() throws Exception {
        Mockito.doReturn("popo").when(tokenProvider).extractId(eq("token"));
        Mockito.doReturn(List.of(anyBooking(), anyBooking())).when(bookingService).getMyBookings(eq("popo"));

        mockMvc.perform(get("/bookings/mine")
                .cookie(new Cookie("token", "token"))
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$..['id','member','gym','date']").exists());
    }

    @Test
    @DisplayName("로그인하지 않고 내 예약을 조회하려 하면 UNAUTHORIZED를 응답한다.")
    void getMyBookingsShouldLogin() throws Exception {
        Mockito.doThrow(AuthenticationException.class).when(tokenProvider).extractId(any());

        mockMvc.perform(get("/bookings/mine"))
            .andExpect(status().isUnauthorized());
    }
}
