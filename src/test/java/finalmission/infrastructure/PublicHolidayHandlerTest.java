package finalmission.infrastructure;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class PublicHolidayHandlerTest {

    private static final String SECRET_KEY = "GbJmfStUjTMn4fHb4w7jqlBGGIk9MnVLW7W22Ig1D6h3A7YaxvfDrAHjHhVGk1a%2BGhIcYysrZ%2BUovxZExR%2BmkA%3D%3D";

    private final PublicHolidayHandler publicHolidayHandler = new PublicHolidayHandler(SECRET_KEY);

    @Test
    void testHoliday() {
        // given
        LocalDate memorialDay = LocalDate.of(2025, 6, 6); // 2025년 현충일

        // then
        assertTrue(publicHolidayHandler.isPublicHoliday(memorialDay));
    }
}
