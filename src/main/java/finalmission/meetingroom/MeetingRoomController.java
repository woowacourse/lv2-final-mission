package finalmission.meetingroom;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    @PostMapping("/meetingrooms")
    public ResponseEntity<MeetingRoomResponse> create() {
        MeetingRoomResponse response = meetingRoomService.create();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/meetingrooms")
    public ResponseEntity<List<MeetingRoomResponse>> findAll() {
        List<MeetingRoomResponse> response = meetingRoomService.findAll();
        return ResponseEntity.ok().body(response);
    }
}
