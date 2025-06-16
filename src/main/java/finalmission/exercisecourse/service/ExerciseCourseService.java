package finalmission.exercisecourse.service;

import finalmission.exercisecourse.domain.ExerciseCourse;
import finalmission.exercisecourse.dto.ExerciseCourseRequest;
import finalmission.exercisecourse.dto.ExerciseCourseResponse;
import finalmission.exercisecourse.infrastructure.ExerciseCourseRepository;
import finalmission.reservation.infrastructure.ReservationRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExerciseCourseService {
    private final ExerciseCourseRepository exerciseCourseRepository;
    private final ReservationRepository reservationRepository;

    public ExerciseCourseService(ExerciseCourseRepository exerciseCourseRepository, ReservationRepository reservationRepository) {
        this.exerciseCourseRepository = exerciseCourseRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional(readOnly = true)
    public List<ExerciseCourseResponse> getExerciseCourses() {
        return exerciseCourseRepository.findAll().stream()
                .map(ExerciseCourseResponse::from)
                .toList();
    }

    @Transactional
    public ExerciseCourseResponse save(ExerciseCourseRequest request) {
        if (exerciseCourseRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("이미 존재하는 클래스 이름입니다.");
        }
        ExerciseCourse exerciseCourse = ExerciseCourse.createWithoutId(request.name(), request.description());
        ExerciseCourse savedExerciseCourse = exerciseCourseRepository.save(exerciseCourse);
        return ExerciseCourseResponse.from(savedExerciseCourse);
    }

    @Transactional
    public void delete(Long id) {
        if (reservationRepository.existsByExerciseCourseId(id)) {
            throw new IllegalArgumentException("예약 중인 클래스입니다.");
        }
        exerciseCourseRepository.deleteById(id);
    }
}
