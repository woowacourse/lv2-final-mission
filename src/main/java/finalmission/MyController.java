package finalmission;

import finalmission.domain.*;
import finalmission.dto.RoomCreateResponse;
import finalmission.dto.TimeAddRequest;
import finalmission.dto.TimeResponses;
import finalmission.dto.TimeStaticsResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyController {

    private final RoomRepository roomRepository;
    private final TimeRepository timeRepository;

    @PostMapping("/room")
    public RoomCreateResponse createRoom() {
        Room room = new Room();
        roomRepository.save(room);
        return RoomCreateResponse.from(room);
    }

    @PostMapping("/room/{roomId}")
    public void addTime(
            @PathVariable("roomId") String roomId,
            @RequestBody TimeAddRequest request
    ) {
        Room room = roomRepository.findById(new Id(roomId)).orElseThrow();
        List<Time> createdTimes = request.values().stream()
                .map(dateTime -> room.createTime(request.username(), dateTime.toLocalDate(), dateTime.toLocalTime()))
                .toList();
        timeRepository.saveAll(createdTimes);
    }

    @GetMapping("/room/{roomId}")
    public TimeStaticsResponses getTimeStatics(
            @PathVariable("roomId") String roomId
    ) {
        List<Time> times = timeRepository.getTimesByRoom_Id(new Id(roomId));
        List<TimeStatics> statics = Time.calculateStatics(times);
        return TimeStaticsResponses.from(statics);
    }

    @GetMapping("/room/{roomId}/my")
    public TimeResponses getMyTimes(
            @PathVariable("roomId") String roomId,
            @RequestParam("username") String username
    ) {
        Room room = roomRepository.findById(new Id(roomId)).orElseThrow();
        List<Time> times = room.getTimesOf(username);
        List<LocalDateTime> dateTimes = times.stream()
                .map(time -> LocalDateTime.of(time.getDate(), time.getTime()))
                .toList();
        return TimeResponses.from(username, dateTimes);
    }

    @DeleteMapping("/room/{roomId}")
    public void deleteAllOf(
            @PathVariable("roomId") String roomId,
            @RequestParam("username") String username
    ) {
        timeRepository.deleteAllByRoom_IdAndUsername(new Id(roomId), username);
    }
}
