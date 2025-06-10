package finalmission.domain.repository;

import finalmission.domain.YogaClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<YogaClass, Long> {
}
