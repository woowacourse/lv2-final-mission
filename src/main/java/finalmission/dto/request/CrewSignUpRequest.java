package finalmission.dto.request;


import finalmission.domain.Crew;
import finalmission.domain.EducationPart;

public record CrewSignUpRequest(
        String name,
        String email,
        String password,
        int period,
        EducationPart educationPart
) {

    public Crew toCrew() {
        return Crew.builder()
                .name(name)
                .email(email)
                .password(password)
                .period(period)
                .educationPart(educationPart)
                .build();
    }
}
