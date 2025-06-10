package finalmission.service;

import finalmission.domain.*;
import finalmission.dto.response.TimeResponses;
import finalmission.dto.response.TimeStaticsResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final RoomRepository roomRepository;
    private final TimeRepository timeRepository;

    @Transactional
    public void addTime(String roomId, Voter voter, List<LocalDateTime> values) {
        Room room = roomRepository.findById(new Id(roomId)).orElseThrow();

        List<Time> createdTimes = values.stream()
                .map(dateTime -> room.createTime(voter, dateTime))
                .toList();
        timeRepository.saveAll(createdTimes);
    }

    @Transactional(readOnly = true)
    public TimeStaticsResponses getTimeStatics(String roomId) {
        List<Time> times = timeRepository.getTimesByRoom_Id(new Id(roomId));
        List<TimeStatics> statics = Time.calculateStatics(times);
        return TimeStaticsResponses.from(statics);
    }

    @Transactional(readOnly = true)
    public TimeResponses getMyTimes(String roomId, Voter voter) {
        Room room = roomRepository.findById(new Id(roomId)).orElseThrow();
        List<Time> times = room.getTimesOf(voter);
        List<LocalDateTime> dateTimes = times.stream()
                .map(Time::getDateTime)
                .toList();
        return TimeResponses.from(voter.getName(), dateTimes);
    }

    @Transactional
    public void dropMyTimes(String roomId, Voter voter) {
        timeRepository.deleteAllByRoom_IdAndVoter(new Id(roomId), voter);
    }
}
