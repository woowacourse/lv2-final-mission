package finalmission.exercisecourse.presentation;

import static finalmission.exercisecourse.presentation.ExerciseCourseController.EXERCISE_COURSE_BASE_URL;

import finalmission.exercisecourse.dto.ExerciseCourseRequest;
import finalmission.exercisecourse.dto.ExerciseCourseResponse;
import finalmission.exercisecourse.service.ExerciseCourseService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EXERCISE_COURSE_BASE_URL)
public class ExerciseCourseController {

    public static final String EXERCISE_COURSE_BASE_URL = "/courses";
    private final ExerciseCourseService exerciseCourseService;

    public ExerciseCourseController(ExerciseCourseService exerciseCourseService) {
        this.exerciseCourseService = exerciseCourseService;
    }

    @GetMapping
    public ResponseEntity<List<ExerciseCourseResponse>> getReservationTimes() {
        List<ExerciseCourseResponse> response = exerciseCourseService.getExerciseCourses();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ExerciseCourseResponse> save(
            @RequestBody final ExerciseCourseRequest request) {
        ExerciseCourseResponse response = exerciseCourseService.save(request);
        URI locationUri = URI.create(EXERCISE_COURSE_BASE_URL + "/" + response.id());
        return ResponseEntity.created(locationUri).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        exerciseCourseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
