package finalmission.controller;

import finalmission.dto.request.RegisterRequest;
import finalmission.service.VoterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class VoterController {

    private final VoterService voterService;

    @PostMapping("/voter/{roomId}")
    public ResponseEntity<Void> register(
            @PathVariable("roomId") String roomId,
            @RequestBody RegisterRequest request
    ) {
        voterService.register(roomId, request.name(), request.password());
        URI createdLocation = URI.create("/time/" + roomId + "/my");
        return ResponseEntity.created(createdLocation).build();
    }
}
