package finalmission.exercisecourse.infrastructure;

import finalmission.exercisecourse.domain.ExerciseCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseCourseRepository extends JpaRepository<ExerciseCourse, Long> {
    boolean existsByName(String name);
}
