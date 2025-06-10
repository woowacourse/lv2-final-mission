package finalmission.exercisecourse.dto;

import finalmission.exercisecourse.domain.ExerciseCourse;

public record ExerciseCourseResponse(
        Long id,
        String name,
        String description
) {
    public static ExerciseCourseResponse from(ExerciseCourse exerciseCourse) {
        return new ExerciseCourseResponse(
                exerciseCourse.getId(),
                exerciseCourse.getName(),
                exerciseCourse.getDescription());
    }
}
