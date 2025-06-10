package finalmission.mungPlan.infra;

import static finalmission.mungPlan.TestFixture.DEFAULT_DATE;

import finalmission.mungPlan.domain.PlanDate;
import finalmission.mungPlan.domain.TimeSlot;
import finalmission.mungPlan.domain.TimeSlots;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PlanDateRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private PlanDateRepository planDateRepository;

    @DisplayName("PlanDate를 저장할 수 있다.")
    @Test
    void savePlanDate() {
        // given
        TimeSlots timeSlots = new TimeSlots();
        timeSlots.addTime(new TimeSlot(LocalTime.of(10, 0), LocalTime.of(11, 0)));
        PlanDate planDate = PlanDate.createNew(DEFAULT_DATE, timeSlots);

        // when
        planDateRepository.save(planDate);

        // then

    }
}
