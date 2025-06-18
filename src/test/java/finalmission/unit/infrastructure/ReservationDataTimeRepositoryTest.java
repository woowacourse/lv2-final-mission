package finalmission.unit.infrastructure;

import finalmission.domain.ReservationDateTime;
import finalmission.infrastructure.ReservationDateTimeRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@DataJpaTest
@Sql(value = "/sql/reservationDateTime.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class ReservationDataTimeRepositoryTest {

    @Autowired
    private ReservationDateTimeRepository reservationDateTimeRepository;

    @Test
    void id로_조회() {
        //when & then
        Optional<ReservationDateTime> dateTime = reservationDateTimeRepository.findById(1L);

        SoftAssertions soft = new SoftAssertions();

        soft.assertThat(dateTime).isPresent();
        soft.assertThat(dateTime.get().getDate()).isEqualTo(LocalDate.of(2025, 5, 5));
        soft.assertThat(dateTime.get().getStartAt()).isEqualTo(LocalTime.of(10, 0));
        soft.assertAll();
    }
}
