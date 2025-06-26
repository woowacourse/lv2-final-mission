package finalmission.reservation.business;

import finalmission.reservation.model.Time;
import finalmission.reservation.presentation.dto.request.TimeCreateWebRequest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional
class TimeServiceTest {

    @Autowired
    private TimeService timeService;

    @Test
    void 시간을_생성하여_저장한다() {
        // Given
        LocalTime startAt = LocalTime.now().plusMinutes(1);
        TimeCreateWebRequest timeCreateWebRequest = new TimeCreateWebRequest(startAt);

        // When
        Time newTime = timeService.createTime(timeCreateWebRequest);

        // Then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(newTime.getId()).isNotNull();
            softAssertions.assertThat(newTime.getStartAt()).isEqualTo(startAt);
        });
    }

    @Test
    void 모든_시간을_조회한다() {
        // Given
        LocalTime startAt1 = LocalTime.now().plusMinutes(1);
        LocalTime startAt2 = LocalTime.now().plusMinutes(2);
        TimeCreateWebRequest timeCreateWebRequest1 = new TimeCreateWebRequest(startAt1);
        TimeCreateWebRequest timeCreateWebRequest2 = new TimeCreateWebRequest(startAt2);
        Time newTime1 = timeService.createTime(timeCreateWebRequest1);
        Time newTime2 = timeService.createTime(timeCreateWebRequest2);

        // When & Then
        assertThat(timeService.findAllTimes())
                .containsExactlyInAnyOrder(newTime1, newTime2);
    }
}
