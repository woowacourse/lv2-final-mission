package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    private String content;

    @Enumerated(EnumType.STRING)
    private MeetingStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Coach coach;

    @ManyToOne(fetch = FetchType.LAZY)
    private Crew crew;

    @Builder
    public Meeting(LocalDateTime dateTime, String content, MeetingStatus status, Coach coach, Crew crew) {
        this.dateTime = dateTime;
        this.content = content;
        this.status = status;
        this.coach = coach;
        this.crew = crew;
    }

    public void updateStatusTo(MeetingStatus meetingStatus) {
        this.status = meetingStatus;
    }

    public boolean isNotOwnerCrew(Long crewId) {
        return !Objects.equals(crew.getId(), crewId);
    }

    public void update(String content) {
        this.content = content;
    }
}
