package finalmission.domain;

import java.util.List;
import java.util.Optional;

public interface LectureRepository {

    List<Lecture> findAll();

    Optional<Lecture> findById(long id);
}
