package finalmission.infrastructure.jpa;

import finalmission.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
}
