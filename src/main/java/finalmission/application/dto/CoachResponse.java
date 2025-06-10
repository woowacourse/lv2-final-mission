package finalmission.application.dto;

import finalmission.domain.Coach;

public class CoachResponse {
    private Long id;
    private String nickname;
    private String education;

    private CoachResponse() {
    }

    public CoachResponse(final Coach coach) {
        this.id = coach.getId();
        this.nickname = coach.getMember().getNickname();
        this.education = coach.getEducation().name();
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEducation() {
        return education;
    }
}
