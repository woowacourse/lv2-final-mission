package finalmission.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.controller.dto.ReservationRequest;
import finalmission.controller.dto.ReservationUpdateRequest;
import finalmission.domain.Reservation;
import finalmission.infrastructure.MailGunClient;
import finalmission.repository.ReservationRepository;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest extends BaseCookie {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReservationRepository reservationRepository;

    @MockitoBean
    private MailGunClient mailClient;

    @Test
    void 모든_예약_현황을_조회한다() throws Exception {
        // given
        Cookie cookie = createFixtureCookie();

        // when && then
        mockMvc.perform(get("/reservations")
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservations[0].id").value(1))
                .andExpect(jsonPath("$.reservations[0].room").value("어드레스룸"))
                .andExpect(jsonPath("$.reservations[0].date").value("2025-06-15"))
                .andExpect(jsonPath("$.reservations[0].startTime").value("19:00"))
                .andExpect(jsonPath("$.reservations[0].endTime").value("20:00"))
                .andExpect(jsonPath("$.reservations[1].id").value(2))
                .andExpect(jsonPath("$.reservations[1].room").value("백스윙룸"))
                .andExpect(jsonPath("$.reservations[1].date").value("2025-06-16"))
                .andExpect(jsonPath("$.reservations[1].startTime").value("20:00"))
                .andExpect(jsonPath("$.reservations[1].endTime").value("21:00"))
        ;
    }

    @Test
    void 예약을_상세_조회_한다() throws Exception {
        // given
        Cookie cookie = createFixtureCookie();
        long reservationId = 1L;

        // when && then
        mockMvc.perform(get("/reservations/" + reservationId)
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nickname").value("테스트1"))
                .andExpect(jsonPath("$.room.id").value(1))
                .andExpect(jsonPath("$.room.name").value("어드레스룸"))
                .andExpect(jsonPath("$.date").value("2025-06-15"))
                .andExpect(jsonPath("$.startTime").value("19:00"))
                .andExpect(jsonPath("$.endTime").value("20:00"))
        ;
    }

    @Test
    void 사용자가_아닌_경우_예약_상세_조회시_예외가_발생한다() throws Exception {
        // given
        Cookie cookie = createFixtureCookie();
        long reservationId = 3L;

        // when && then
        mockMvc.perform(get("/reservations/" + reservationId)
                        .cookie(cookie))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("다른 사용자 예약을 상세 열람할 수 없습니다."))
                .andExpect(jsonPath("$.errorCode").value(400));
    }

    @Test
    void 회의실을_예약한다() throws Exception {
        // given
        Cookie cookie = createFixtureCookie();

        Long roomId = 1L;
        LocalDate date = LocalDate.of(2025, 6, 15);
        LocalTime startTime = LocalTime.of(18, 10);
        LocalTime endTime = LocalTime.of(19, 0);

        ReservationRequest request = new ReservationRequest(roomId, date, startTime, endTime);

        doNothing().when(mailClient)
                .sendReservationMail(any(Reservation.class));

        // when && then
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .cookie(cookie))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.room").value("어드레스룸"))
                .andExpect(jsonPath("$.date").value("2025-06-15"))
                .andExpect(jsonPath("$.startTime").value("18:10"))
                .andExpect(jsonPath("$.endTime").value("19:00"));
    }

    @Test
    void 회의실_예약을_수정한다() throws Exception {
        // given
        Cookie cookie = createFixtureCookie();

        long updateReservationId = 1L;
        Long roomId = 2L;
        LocalDate date = LocalDate.of(2025, 6, 15);
        LocalTime startTime = LocalTime.of(19, 0);
        LocalTime endTime = LocalTime.of(20, 0);

        ReservationUpdateRequest request = new ReservationUpdateRequest(roomId, date,
                startTime, endTime);

        // when && then
        mockMvc.perform(patch("/reservations/" + updateReservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.room").value("백스윙룸"))
                .andExpect(jsonPath("$.date").value("2025-06-15"))
                .andExpect(jsonPath("$.startTime").value("19:00"))
                .andExpect(jsonPath("$.endTime").value("20:00"));
    }

    @Test
    void 사용자가_아닌_예약_수정시_예외가_발생한다() throws Exception {
        // given
        Cookie cookie = createFixtureCookie();

        long updateReservationId = 3L;
        Long roomId = 2L;
        LocalDate date = LocalDate.of(2025, 6, 15);
        LocalTime startTime = LocalTime.of(19, 0);
        LocalTime endTime = LocalTime.of(20, 0);

        ReservationUpdateRequest request = new ReservationUpdateRequest(roomId, date,
                startTime, endTime);

        // when && then
        ResultActions actions = mockMvc.perform(patch("/reservations/" + updateReservationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .cookie(cookie));

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("다른 사용자 예약을 수정할 수 없습니다."))
                .andExpect(jsonPath("$.errorCode").value(400));
    }

    @Test
    void 회의실_예약을_삭제한다() throws Exception {
        // given
        Cookie cookie = createFixtureCookie();
        long deleteReservationId = 1L;

        boolean existed = reservationRepository.existsById(deleteReservationId);

        // when
        ResultActions actions = mockMvc.perform(delete("/reservations/" + deleteReservationId)
                .cookie(cookie));

        boolean result = reservationRepository.existsById(deleteReservationId);

        // then
        actions.andExpect(status().isNoContent());
        assertThat(result).isNotEqualTo(existed);
    }

    @Test
    void 사용자가_아닌_예약_삭제시_예외가_발생한다() throws Exception {
        // given
        Cookie cookie = createFixtureCookie();
        long deleteReservationId = 3L;

        // when && then
        ResultActions actions = mockMvc.perform(delete("/reservations/" + deleteReservationId)
                .cookie(cookie));

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("다른 사용자 예약을 삭제할 수 없습니다."))
                .andExpect(jsonPath("$.errorCode").value(400));
    }
}
