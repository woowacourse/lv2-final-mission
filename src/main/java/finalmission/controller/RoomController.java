package finalmission.controller;

import finalmission.config.MemberId;
import finalmission.controller.dto.RoomCreateRequest;
import finalmission.controller.dto.RoomCreateResponse;
import finalmission.controller.dto.RoomResponse;
import finalmission.controller.dto.RoomWithoutParticipantsResponse;
import finalmission.service.RoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomCreateResponse> create(
            @RequestBody final RoomCreateRequest request,
            @MemberId final Long memberId
    ) {
        final RoomCreateResponse response = roomService.create(request, memberId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable("id") final Long id) {
        final RoomResponse response = roomService.getById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/member")
    public ResponseEntity<List<RoomWithoutParticipantsResponse>> findByMemberId(
            @MemberId final Long memberId
    ) {
        final List<RoomWithoutParticipantsResponse> response = roomService.findByMemberId(memberId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RoomWithoutParticipantsResponse>> findAll() {
        final List<RoomWithoutParticipantsResponse> response = roomService.findAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/join/{id}")
    public ResponseEntity<Void> join(
            @MemberId final Long memberId,
            @PathVariable("id") final Long roomId
    ) {
        roomService.join(roomId, memberId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/leave/{id}")
    public ResponseEntity<Void> leave(
            @MemberId final Long memberId,
            @PathVariable("id") final Long roomId
    ) {
        roomService.leave(roomId, memberId);

        return ResponseEntity.noContent().build();
    }
}
