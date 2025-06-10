package finalmission.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookingTest {

    @Test
    @DisplayName("예약을 생성한다.")
    void newBooking() {
        var booking = new Booking(
            new Member("popo", "password", "포포"),
            new Gym("짐박스", new Address("군자로123-1", "지하 1층")),
            LocalDate.of(2025, 6, 17)
        );
        assertThat(booking).isNotNull();
    }
}
