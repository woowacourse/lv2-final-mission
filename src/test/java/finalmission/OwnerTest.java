package finalmission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

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

import finalmission.owner.dto.OwnerRequest;
import finalmission.owner.repository.OwnerRepository;
import finalmission.shop.domain.OperatingHour;
import finalmission.shop.domain.ShopType;
import finalmission.shop.dto.ShopResponse;
import finalmission.shop.repository.ReservationRepository;
import finalmission.shop.repository.ShopRepository;
import finalmission.user.dto.UserRequest;
import finalmission.user.dto.UserResponse;

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
@Sql("/data.sql")
class OwnerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private OwnerRepository ownerRepository;

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
    void register() throws Exception {
        // given
        var loginRequest = new UserRequest.Login("moko@example.com", "1234");
        String responseBody = mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn()
                .getResponse()
                .getContentAsString();
        UserResponse.Login response = objectMapper.readValue(responseBody, UserResponse.Login.class);
        token = response.token();

        var request = new OwnerRequest.Register("aaa", "123");

        // when
        mockMvc.perform(post("/owners/register")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // then
        assertThat(ownerRepository.findAll()).hasSize(DataConstant.OWNER_COUNT + 1);
    }

    @Test
    void registerShop() throws Exception {
        // given
        var request = new OwnerRequest.Shop("aaa", ShopType.분식, "bbb",
                List.of(new OwnerRequest.Shop.OperatingHour(DayOfWeek.SUNDAY, LocalTime.of(10, 0))));

        // when
        String responseBody = mockMvc.perform(post("/owners/register/shops")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var response = objectMapper.readValue(responseBody, ShopResponse.Detail.class);

        // then
        assertThat(response.name()).isEqualTo("aaa");
        assertThat(response.type()).isEqualTo(ShopType.분식);
        assertThat(response.detail()).isEqualTo("bbb");
        assertThat(response.innerOperatingHours().getFirst()).isEqualTo(
                ShopResponse.Detail.InnerOperatingHour.of(List.of(
                        new OperatingHour(shopRepository.getById(response.id()), DayOfWeek.SUNDAY, LocalTime.of(10,
                                0)))).getFirst());
    }

    @Test
    void updateShop() throws Exception {
        // given
        var registerRequest = new OwnerRequest.Shop("aaa", ShopType.분식, "bbb",
                List.of(new OwnerRequest.Shop.OperatingHour(DayOfWeek.SUNDAY, LocalTime.of(10, 0))));
        String registerResponseBody = mockMvc.perform(post("/owners/register/shops")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var registerResponse = objectMapper.readValue(registerResponseBody, ShopResponse.Detail.class);
        Long shopId = registerResponse.id();

        // when
        var request = new OwnerRequest.Shop("newName", ShopType.한식, "newDetail",
                List.of(new OwnerRequest.Shop.OperatingHour(DayOfWeek.MONDAY, LocalTime.of(20, 0))));
        String responseBody = mockMvc.perform(put("/owners/shops/" + shopId)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var response = objectMapper.readValue(responseBody, ShopResponse.Detail.class);

        // then
        assertThat(response.name()).isEqualTo("newName");
        assertThat(response.type()).isEqualTo(ShopType.한식);
        assertThat(response.detail()).isEqualTo("newDetail");
        assertThat(response.innerOperatingHours().getFirst()).isEqualTo(
                ShopResponse.Detail.InnerOperatingHour.of(List.of(
                        new OperatingHour(shopRepository.getById(response.id()), DayOfWeek.MONDAY, LocalTime.of(20,
                                0)))).getFirst());
    }
}
