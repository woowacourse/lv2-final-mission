package finalmission.mungPlan.infra;

import static finalmission.mungPlan.TestFixture.DEFAULT_DATE;

import finalmission.mungPlan.domain.PlanDate;
import finalmission.mungPlan.domain.TimeSlot;
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
        PlanDate planDate = PlanDate.createNew(DEFAULT_DATE);
        TimeSlot timeSlot = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(11, 0));
        planDate.addTimeSlot(timeSlot);
        // when
        planDateRepository.save(planDate);

        // then

    }
}
