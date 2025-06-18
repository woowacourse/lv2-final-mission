package finalmission.controller;

import finalmission.annotation.CheckRole;
import finalmission.domain.MemberRole;
import finalmission.dto.request.CreateMeetingRoomRequest;
import finalmission.dto.response.CreateMeetingRoomResponse;
import finalmission.dto.response.MeetingRoomResponse;
import finalmission.service.MeetingRoomService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meeting-rooms")
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    public MeetingRoomController(final MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    @GetMapping
    public List<MeetingRoomResponse> getAllMeetingRoom() {
        return meetingRoomService.findAllMeetingRoom();
    }

    @CheckRole({MemberRole.ADMIN})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateMeetingRoomResponse addMeetingRoom(@Valid @RequestBody final CreateMeetingRoomRequest request) {
        return meetingRoomService.addMeetingRoom(request);
    }

    @CheckRole({MemberRole.ADMIN})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeetingRoom(@PathVariable("id") final Long id) {
        meetingRoomService.deleteMeetingRoom(id);
    }
}
