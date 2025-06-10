package finalmission.mungPlan.infra;

import finalmission.mungPlan.domain.PlanDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanDateRepository extends JpaRepository<PlanDate, Long> {
}
