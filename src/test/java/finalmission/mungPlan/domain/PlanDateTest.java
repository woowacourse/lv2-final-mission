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

        // when

        PlanDate planDate = PlanDate.createNew(DEFAULT_DATE);
        TimeSlot timeSlot = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(11, 0));
        planDate.addTimeSlot(timeSlot);

        // then
        System.out.println(planDate.getDate());
    }
}
