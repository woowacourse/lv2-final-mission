package finalmission.planning;

import finalmission.planning.domain.PlanDate;
import finalmission.planning.domain.TimeSlot;
import java.time.LocalDate;
import java.time.LocalTime;

public class TestFixture {

    public static final PlanDate DEFAULT_PLAN_DATE = new PlanDate(LocalDate.now().plusDays(1));
    public static final TimeSlot DEFAULT_TIME_SLOT = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0));
}
