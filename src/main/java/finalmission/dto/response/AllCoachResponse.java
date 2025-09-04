package finalmission.dto.response;

import finalmission.domain.Coach;
import finalmission.domain.EducationPart;

public record AllCoachResponse(
        Long coachId,
        String coachName,
        EducationPart educationPart
) {

    public static AllCoachResponse from(Coach coach) {
        return new AllCoachResponse(
                coach.getId(),
                coach.getName(),
                coach.getEducationPart()
        );
    }
}
