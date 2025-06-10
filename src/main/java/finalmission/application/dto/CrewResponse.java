package finalmission.application.dto;

import finalmission.domain.Crew;

public class CrewResponse {
    private Long id;
    private String nickname;

    private CrewResponse() {
    }

    public CrewResponse(final Crew crew) {
        this.id = crew.getId();
        this.nickname = crew.getMember().getNickname();
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
}
