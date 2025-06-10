package finalmission;

import finalmission.domain.*;
import finalmission.dto.RoomCreateResponse;
import finalmission.dto.TimeAddRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
                .flatMap(value -> value.times().stream()
                        .map(time -> room.createTime(request.username(), value.date(), time))
                ).toList();
        timeRepository.saveAll(createdTimes);
    }
}
