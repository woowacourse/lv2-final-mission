package finalmission.service;

import finalmission.domain.Coach;
import finalmission.domain.Crew;
import finalmission.domain.Meeting;
import finalmission.domain.MeetingStatus;
import finalmission.dto.request.CreateMeetingRequest;
import finalmission.dto.request.MeetingAnswerRequest;
import finalmission.dto.request.UpdateMeetingRequest;
import finalmission.dto.response.AllMeetingResponse;
import finalmission.dto.response.MeetingAppliedCrewResponse;
import finalmission.dto.response.MeetingResponse;
import finalmission.repository.CoachRepository;
import finalmission.repository.CrewRepository;
import finalmission.repository.MeetingRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingService {

    private static final int MEETING_TIME = 1;

    private final MeetingRepository meetingRepository;
    private final CoachRepository coachRepository;
    private final CrewRepository crewRepository;

    //TODO : LocalDateTime 변환 로직 null 체크하기
    @Transactional
    public void create(Long crewId, CreateMeetingRequest request) {
        validateOverlappedDateTime(LocalDateTime.of(request.date(), request.time()));
        MeetingStatus meetingStatus = MeetingStatus.PENDING;
        Coach coach = getCoachById(request.coachId());
        Crew crew = getCrewById(crewId);

        Meeting meeting = request.toMeeting(meetingStatus, coach, crew);
        meetingRepository.save(meeting);
    }

    @Transactional
    public void updateAnswer(Long meetingId, MeetingAnswerRequest request) {
        Meeting meeting = getMeetingById(meetingId);
        meeting.updateStatusTo(request.answer());
    }

    @Transactional
    public void update(Long meetingId, Long crewId, UpdateMeetingRequest request) {
        Meeting meeting = getMeetingById(meetingId);
        validateOwnerCrew(crewId, meeting);
        meeting.update(request.content());
    }

    // TODO : N+1 문제 해결하기
    public List<MeetingAppliedCrewResponse> getAllMeetingApplicantByCoachId(Long coachId) {
        List<Meeting> meetings = meetingRepository.findAllByCoachId(coachId);
        return meetings.stream()
                .map(MeetingAppliedCrewResponse::from)
                .toList();
    }

    // TODO : N+1 코치
    public List<AllMeetingResponse> getAllByCrewId(Long crewId) {
        List<Meeting> meetings = meetingRepository.findAllByCrewId(crewId);
        return meetings.stream()
                .map(AllMeetingResponse::from)
                .toList();
    }

    public MeetingResponse getByIdAndCrewId(Long meetingId, Long crewId) {
        Meeting meeting = meetingRepository.findByIdAndCrewId(meetingId, crewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID와 크루ID로 미팅을 찾을 수 없습니다"));
        return MeetingResponse.from(meeting);
    }

    private void validateOwnerCrew(Long crewId, Meeting meeting) {
        if (meeting.isNotOwnerCrew(crewId)) {
            throw new IllegalArgumentException("현재 로그인된 크루는 해당 미팅을 수정할 수 없습니다.");
        }
    }

    private void validateOverlappedDateTime(LocalDateTime dateTime) {
        LocalDateTime overlappedPossibleStartTime = dateTime.minusHours(MEETING_TIME);
        LocalDateTime overlappedPossibleEndTime = dateTime.plusHours(MEETING_TIME);
        if (meetingRepository.existsMeetingByDateTimeBetween(overlappedPossibleStartTime, overlappedPossibleEndTime)) {
            throw new IllegalArgumentException("현재 겹치는 미팅시간이 존재합니다.");
        }
    }

    private Meeting getMeetingById(Long meetingId) {
        return meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID로 미팅을 찾을 수 없습니다"));
    }

    private Coach getCoachById(Long coachId) {
        return coachRepository.findById(coachId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID로 코치를 찾을 수 없습니다"));
    }

    private Crew getCrewById(Long crewId) {
        return crewRepository.findById(crewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID로 크루를 찾을 수 없습니다."));
    }
}
