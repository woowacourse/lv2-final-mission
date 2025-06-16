package finalmission.exercisecourse.service;

import finalmission.exercisecourse.domain.ExerciseCourse;
import finalmission.exercisecourse.dto.ExerciseCourseRequest;
import finalmission.exercisecourse.dto.ExerciseCourseResponse;
import finalmission.exercisecourse.infrastructure.ExerciseCourseRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExerciseCourseService {
    private final ExerciseCourseRepository exerciseCourseRepository;

    public ExerciseCourseService(ExerciseCourseRepository exerciseCourseRepository) {
        this.exerciseCourseRepository = exerciseCourseRepository;
    }

    @Transactional(readOnly = true)
    public List<ExerciseCourseResponse> getExerciseCourses() {
        return exerciseCourseRepository.findAll().stream()
                .map(ExerciseCourseResponse::from)
                .toList();
    }

    @Transactional
    public ExerciseCourseResponse save(ExerciseCourseRequest request) {
//        중복 검증
        ExerciseCourse exerciseCourse = ExerciseCourse.createWithoutId(request.name(), request.description());
        ExerciseCourse savedExerciseCourse = exerciseCourseRepository.save(exerciseCourse);
        return ExerciseCourseResponse.from(savedExerciseCourse);
    }

    @Transactional
    public void delete(Long id) {
//        지우려는 클래스를 예약하고 있는 예약이 있는지 확인
        exerciseCourseRepository.deleteById(id);
    }
}
