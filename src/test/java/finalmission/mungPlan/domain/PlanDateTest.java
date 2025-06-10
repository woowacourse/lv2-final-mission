package finalmission.mungPlan.domain;

import static finalmission.mungPlan.TestFixture.DEFAULT_DATE;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlanDateTest {

    @DisplayName("planDate 생성 테스트")
    @Test
    void createPlanDate() {
        // given
        TimeSlots timeSlots = new TimeSlots();
        timeSlots.addTime(new TimeSlot(LocalTime.of(10, 0), LocalTime.of(11, 0)));

        // when

        PlanDate planDate = PlanDate.createNew(DEFAULT_DATE, timeSlots);

        // then
        System.out.println(planDate.getTimeSlots().getAvailableTimes());

    }
}
