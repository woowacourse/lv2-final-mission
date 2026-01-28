package finalmission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import finalmission.shop.dto.ReservationResponse;
import finalmission.shop.dto.ShopResponse;
import finalmission.shop.repository.ReservationRepository;
import finalmission.user.dto.UserRequest;
import finalmission.user.dto.UserResponse;

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
@Sql("/data.sql")
class ShopTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReservationRepository reservationRepository;

    private String token;

    @BeforeEach
    void setUp() throws Exception {
        // given
        UserRequest.Login request = new UserRequest.Login("meow@example.com", "1234");

        // when
        String responseBody = mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserResponse.Login response = objectMapper.readValue(responseBody, UserResponse.Login.class);
        token = response.token();
    }

    @Test
    void getAll() throws Exception {
        // when
        String responseBody = mockMvc.perform(get("/shops")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ShopResponse.Simple[] response = objectMapper.readValue(responseBody, ShopResponse.Simple[].class);

        // then
        assertThat(response).hasSize(DataConstant.SHOP_COUNT);
    }

    @Test
    void getDetail() throws Exception {
        // given
        Long shopId = 1L;

        // when
        String responseBody = mockMvc.perform(get("/shops/" + shopId)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ShopResponse.Detail response = objectMapper.readValue(responseBody, ShopResponse.Detail.class);

        // then
        assertThat(response.id()).isEqualTo(shopId);
    }

    @Test
    void getAvailableTime() throws Exception {
        // given
        Long shopId = 1L;

        // when
        String responseBody = mockMvc.perform(get("/shops/" + shopId + "/times")
                        .param("date", "2025-06-10")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        LocalTime[] response = objectMapper.readValue(responseBody, LocalTime[].class);

        // then
        assertThat(response[0]).isEqualTo(LocalTime.of(12, 0));
    }

    @Test
    void reserve() throws Exception {
        // given
        Long shopId = 1L;

        // when
        String responseBody = mockMvc.perform(post("/shops/" + shopId)
                        .header("Authorization", token)
                        .param("date", "2025-06-10")
                        .param("time", "12:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var response = objectMapper.readValue(responseBody, ReservationResponse.Created.class);

        // then
        assertThat(response.shop().id()).isEqualTo(shopId);
        assertThat(response.date()).isEqualTo(LocalDate.of(2025, 6, 10));
        assertThat(response.time()).isEqualTo(LocalTime.of(12, 0));
    }

    @Test
    void cancel() throws Exception {
        // given
        Long shopId = 1L;
        Long reservationId = 1L;
        mockMvc.perform(post("/shops/" + shopId)
                        .header("Authorization", token)
                        .param("date", "2025-06-10")
                        .param("time", "12:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(reservationRepository.findById(reservationId)).isNotEmpty();

        // when
        mockMvc.perform(delete("/shops/" + shopId)
                        .header("Authorization", token)
                        .param("reservationId", reservationId.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // then
        assertThat(reservationRepository.findById(reservationId)).isEmpty();
    }

    @Test
    void myReservations() throws Exception {
        // given
        Long shopId = 1L;
        Long reservationId = 1L;
        mockMvc.perform(post("/shops/" + shopId)
                        .header("Authorization", token)
                        .param("date", "2025-06-10")
                        .param("time", "12:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(reservationRepository.findById(reservationId)).isNotEmpty();

        // when
        String responseBody = mockMvc.perform(get("/shops/mine")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var response = objectMapper.readValue(responseBody, ReservationResponse.Simple[].class);

        // then
        assertThat(response[0].shop().id()).isEqualTo(shopId);
        assertThat(response[0].id()).isEqualTo(reservationId);
        assertThat(response[0].date()).isEqualTo(LocalDate.of(2025, 6, 10));
        assertThat(response[0].time()).isEqualTo(LocalTime.of(12, 0));
    }
}
