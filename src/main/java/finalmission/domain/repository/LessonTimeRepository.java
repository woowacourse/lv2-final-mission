package finalmission.domain.repository;

import finalmission.domain.entity.LessonTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonTimeRepository extends JpaRepository<LessonTime, Long> {
}
