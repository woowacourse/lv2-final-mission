package finalmission;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyController {

    private final RoomRepository roomRepository;

    @PostMapping("/room")
    public RoomCreateResponse createRoom() {
        Room room = new Room();
        roomRepository.save(room);
        return RoomCreateResponse.from(room);
    }
}
