package finalmission.meetingRoom.ui;

import finalmission.meetingRoom.application.MeetingRoomService;
import finalmission.meetingRoom.application.dto.MeetingRoomResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("meeting-rooms")
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;

    @GetMapping
    public ResponseEntity<List<MeetingRoomResponse>> findAll() {
        List<MeetingRoomResponse> response = meetingRoomService.findAll();
        return ResponseEntity.ok(response);
    }
}
