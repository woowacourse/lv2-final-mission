package finalmission.service;

import finalmission.domain.*;
import finalmission.dto.RoomCreateResponse;
import finalmission.dto.TimeResponses;
import finalmission.dto.TimeStaticsResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeVoteService {

    private final RoomRepository roomRepository;
    private final TimeRepository timeRepository;

    @Transactional
    public RoomCreateResponse createRoom() {
        Room room = new Room();
        roomRepository.save(room);
        return RoomCreateResponse.from(room);
    }

    @Transactional
    public void addTime(String roomId, String username, List<LocalDateTime> values) {
        Room room = roomRepository.findById(new Id(roomId)).orElseThrow();
        List<Time> createdTimes = values.stream()
                .map(dateTime -> room.createTime(username, dateTime))
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
    public TimeResponses getMyTimes(String roomId, String username) {
        Room room = roomRepository.findById(new Id(roomId)).orElseThrow();
        List<Time> times = room.getTimesOf(username);
        List<LocalDateTime> dateTimes = times.stream()
                .map(Time::getDateTime)
                .toList();
        return TimeResponses.from(username, dateTimes);
    }

    @Transactional
    public void dropMyTimes(String roomId, String username) {
        timeRepository.deleteAllByRoom_IdAndUsername(new Id(roomId), username);
    }
}
