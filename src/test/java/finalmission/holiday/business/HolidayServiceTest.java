package finalmission.holiday.business;

import finalmission.holiday.model.Holiday;
import finalmission.holiday.presentation.dto.request.HolidayCreateWebRequest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional
class HolidayServiceTest {

    @Autowired
    private HolidayService holidayService;

    @Test
    void 휴일을_생성하여_저장한다() {
        // Given
        LocalDate date = LocalDate.now().plusDays(1);
        String name = "테스트 휴일";
        HolidayCreateWebRequest holidayCreateWebRequest = new HolidayCreateWebRequest(date, name);

        // When
        Holiday newHoliday = holidayService.create(holidayCreateWebRequest);

        // Then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(newHoliday.getId()).isNotNull();
            softAssertions.assertThat(newHoliday.getDate()).isEqualTo(date);
            softAssertions.assertThat(newHoliday.getName()).isEqualTo(name);
        });
    }
}