package finalmission.support;

import finalmission.domain.Coach;
import finalmission.domain.Crew;
import finalmission.domain.EducationPart;
import finalmission.domain.Meeting;
import finalmission.domain.MeetingStatus;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Fixture {

    private static int identifier = 0;

    public static Coach createCoach(String authId, String password) {
        identifier++;
        return Coach.builder()
                .authId(authId)
                .password(password)
                .name("테스트코치" + identifier)
                .educationPart(EducationPart.BACKEND)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(18, 0))
                .build();
    }

    public static Coach createCoach() {
        identifier++;
        return Coach.builder()
                .authId("authid" + identifier)
                .password("password")
                .name("테스트코치" + identifier)
                .educationPart(EducationPart.BACKEND)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(18, 0))
                .build();
    }

    public static Crew createCrew(String email, String password) {
        identifier++;
        return Crew.builder()
                .email(email)
                .password(password)
                .educationPart(EducationPart.BACKEND)
                .period(7)
                .name("wade")
                .build();
    }

    public static Crew createCrew() {
        identifier++;
        return Crew.builder()
                .email("email" + identifier)
                .password("password")
                .educationPart(EducationPart.BACKEND)
                .period(7)
                .name("wade")
                .build();
    }

    public static Meeting createPendingMeeting(Crew savedCrew, Coach savedCoach) {
        return Meeting.builder()
                .dateTime(LocalDateTime.of(2025, 7, 1, 14, 0))
                .content("미팅 신청")
                .coach(savedCoach)
                .crew(savedCrew)
                .status(MeetingStatus.PENDING)
                .build();
    }
}
