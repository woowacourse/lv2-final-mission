package finalmission.infrastructure;

import finalmission.domain.Lecture;
import finalmission.domain.LectureRepository;
import finalmission.infrastructure.jpa.LectureJpaRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public List<Lecture> findAll() {
        return List.of();
    }

    @Override
    public Optional<Lecture> findById(final long id) {
        return lectureJpaRepository.findById(id);
    }
}
