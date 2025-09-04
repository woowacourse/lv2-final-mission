package finalmission.service;

import static finalmission.domain.TokenAuthRole.ADMIN;

import finalmission.domain.Coach;
import finalmission.domain.Holiday;
import finalmission.domain.HolidayExtractor;
import finalmission.domain.Meeting;
import finalmission.domain.TokenProcessor;
import finalmission.dto.request.CoachLoginRequest;
import finalmission.dto.request.UpdateMeetingTimeRequest;
import finalmission.dto.response.AllCoachResponse;
import finalmission.dto.response.CoachLoginResponse;
import finalmission.dto.response.CoachResponse;
import finalmission.repository.CoachRepository;
import finalmission.repository.MeetingRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoachService {

    private final CoachRepository coachRepository;
    private final MeetingRepository meetingRepository;
    private final TokenProcessor tokenProcessor;
    private final HolidayExtractor holidayExtractor;

    public CoachLoginResponse login(CoachLoginRequest request) {
        Coach coach = coachRepository.findByAuthIdAndPassword(request.authId(), request.password())
                .orElseThrow(() -> new IllegalArgumentException("코치의 이메일 혹은 비밀번호가 틀렸습니다."));
        String token = tokenProcessor.createToken(ADMIN, coach.getId());

        return new CoachLoginResponse(token);
    }

    public List<AllCoachResponse> getAll() {
        return coachRepository.findAll().stream()
                .map(AllCoachResponse::from)
                .toList();
    }

    public CoachResponse getById(Long id) {
        Coach coach = getCoachById(id);
        List<Meeting> meetingsByCoachId = meetingRepository.findAllByCoachId(id);
        List<LocalDateTime> reservedDateTime = extractReservedDateTime(meetingsByCoachId);
        List<Holiday> holidays = extractHolidays();
        return CoachResponse.of(coach, reservedDateTime, holidays);
    }

    private List<Holiday> extractHolidays() {
        LocalDate date = LocalDate.now();
        return holidayExtractor.extract(date.getYear(), date.getMonthValue());
    }

    @Transactional
    public void updateMeetingTime(Long authenticatedCoachId, UpdateMeetingTimeRequest request) {
        Coach coach = getCoachById(authenticatedCoachId);
        coach.updateMeetingTime(request.startTime(), request.endTime());
    }

    private List<LocalDateTime> extractReservedDateTime(List<Meeting> meetings) {
        return meetings.stream()
                .map(Meeting::getDateTime)
                .filter(dateTime -> dateTime.isAfter(LocalDateTime.now()))
                .toList();
    }

    private Coach getCoachById(Long id) {
        return coachRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id로 코치를 찾을 수 없습니다."));
    }
}
