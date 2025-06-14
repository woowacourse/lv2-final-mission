package finalmission.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.auth.CookieManager;
import finalmission.auth.JwtTokenProvider;
import finalmission.auth.LoginMemberInfo;
import finalmission.member.domain.Member;
import finalmission.member.dto.MemberResponse;
import finalmission.reservation.dto.CreateReservationRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.dto.UpdateReservationRequest;
import finalmission.reservation.service.HolidayService;
import finalmission.reservation.service.ReservationService;
import finalmission.restaurant.dto.RestaurantResponse;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    public static final LocalDateTime RESERVATION_DATE_TIME = LocalDateTime.of(2025, 6, 10, 10, 0, 0);
    public static final long RESTAURANT_ID = 1L;
    public static final long MEMBER_ID = 1L;
    public static final int PERSONNEL = 2;
    public static final String VALID_TOKEN = "header.payload.signature";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CookieManager cookieManager;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @MockitoBean
    private ReservationService reservationService;

    @MockitoBean
    private HolidayService holidayService;

    @Autowired
    private ObjectMapper objectMapper;

    private Member member;

    private Cookie cookie;

    @BeforeEach
    void setUp() {
        member = new Member(MEMBER_ID, "eve", "eve@example.com", "password");
        cookie = new Cookie("token", VALID_TOKEN);

        when(jwtTokenProvider.extractId(any())).thenReturn(MEMBER_ID);
    }

    @Test
    @DisplayName("예약 생성 성공 응답 테스트")
    void createReservationTest1() throws Exception {
        CreateReservationRequest reservationRequest = new CreateReservationRequest(
                RESERVATION_DATE_TIME,
                RESTAURANT_ID,
                PERSONNEL);
        LoginMemberInfo loginMemberInfo = new LoginMemberInfo(MEMBER_ID);
        ReservationResponse reservationResponse = getReservationResponse();
        when(reservationService.save(reservationRequest, loginMemberInfo))
                .thenReturn(reservationResponse);

        mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(objectMapper.writeValueAsString(reservationRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(reservationResponse.id()));
    }

    @Test
    @DisplayName("예약 생성 실패 응답 테스트 - 공휴일")
    void createReservationTest2() throws Exception {
        CreateReservationRequest reservationRequest = new CreateReservationRequest(
                RESERVATION_DATE_TIME,
                RESTAURANT_ID,
                PERSONNEL);
        LoginMemberInfo loginMemberInfo = new LoginMemberInfo(MEMBER_ID);
        ReservationResponse reservationResponse = getReservationResponse();
        when(reservationService.save(reservationRequest, loginMemberInfo))
                .thenReturn(reservationResponse);
        doThrow(IllegalArgumentException.class).when(holidayService).validateHoliday(RESERVATION_DATE_TIME);

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(cookie)
                        .content(objectMapper.writeValueAsString(reservationRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("예약 삭제 성공 테스트")
    void deleteTest() throws Exception {
        LoginMemberInfo loginMemberInfo = new LoginMemberInfo(MEMBER_ID);
        doNothing().when(reservationService).deleteById(1L, loginMemberInfo);

        mockMvc.perform(delete("/reservations/1")
                .cookie(cookie))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("예약 수정 성공 테스트")
    void updateTest1() throws Exception {
        int toUpdatePersonnel = PERSONNEL + 1;
        UpdateReservationRequest reservationRequest = new UpdateReservationRequest(RESERVATION_DATE_TIME, toUpdatePersonnel);
        LoginMemberInfo loginMemberInfo = new LoginMemberInfo(MEMBER_ID);
        ReservationResponse reservationResponse = new ReservationResponse(1L, RESERVATION_DATE_TIME, getMemberResponse(), getRestaurantResponse(), toUpdatePersonnel);
        when(reservationService.updateReservation(1L, reservationRequest, loginMemberInfo))
                .thenReturn(reservationResponse);

        mockMvc.perform(put("/reservations/1")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservationRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.personnel").value(toUpdatePersonnel));
    }

    private ReservationResponse getReservationResponse() {
        MemberResponse memberResponse = getMemberResponse();
        RestaurantResponse restaurantResponse = getRestaurantResponse();
        return new ReservationResponse(
                MEMBER_ID,
                RESERVATION_DATE_TIME,
                memberResponse,
                restaurantResponse,
                PERSONNEL);
    }

    private MemberResponse getMemberResponse() {
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }

    private RestaurantResponse getRestaurantResponse() {
        return new RestaurantResponse(1L, "restaurant", "address", List.of("menu"));
    }
}