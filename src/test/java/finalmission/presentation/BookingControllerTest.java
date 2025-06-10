package finalmission.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import finalmission.application.BookingService;
import finalmission.domain.AuthenticationException;
import finalmission.domain.MemberTokenProvider;
import jakarta.servlet.http.Cookie;
import java.time.LocalDate;
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
    @DisplayName("로그인이 되어있지 않으면 UNAUTHORIZED를 응답한다.")
    void shouldLogin() throws Exception {
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
}
