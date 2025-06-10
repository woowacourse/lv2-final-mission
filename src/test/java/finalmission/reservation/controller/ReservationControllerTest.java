package finalmission.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.auth.JwtTokenProvider;
import finalmission.auth.LoginMemberInfo;
import finalmission.auth.LoginMemberInterceptor;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.reservation.dto.CreateReservationRequest;
import finalmission.reservation.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @MockitoBean
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createReservation() throws Exception {
        when(jwtTokenProvider.extractId(any()))
                .thenReturn(1L);
        Member member = new Member(1L, "name", "email", "password");
        when(loginMemberInterceptor.resolveArgument(any(), any(), any(), any()))
                .thenReturn(new LoginMemberInfo(1L));
        when(memberRepository.findById(any()))
                .thenReturn(Optional.of(member));
        CreateReservationRequest request = new CreateReservationRequest(LocalDateTime.of(2025, 6, 10, 12, 0), 1L, 2);

        mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(any())
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}