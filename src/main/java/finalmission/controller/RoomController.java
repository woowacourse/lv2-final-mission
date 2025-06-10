package finalmission.controller;

import finalmission.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RoomCreateResponse> create(@RequestBody final RoomCreateRequest request) {
        final RoomCreateResponse response = roomService.create(request);

        return ResponseEntity.ok(response);
    }
}
