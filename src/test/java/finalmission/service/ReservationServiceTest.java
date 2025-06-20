package finalmission.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.dto.ReservationCreateRequest;
import finalmission.exception.BadRequestException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Test
    void saveFailTest() {
        //given
        final long memberId = 1L;
        final ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(LocalDate.of(2025, 12, 25), 8, 10, 4);

        //should
        assertThatThrownBy(() -> reservationService.save(memberId, reservationCreateRequest)).isInstanceOf(
                BadRequestException.class);
    }
}
