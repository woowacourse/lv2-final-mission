package finalmission.controller;

import finalmission.dto.request.RegisterRequest;
import finalmission.dto.response.RegisterResponse;
import finalmission.service.VoterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VoterController {

    private final VoterService voterService;

    @PostMapping("/voter/{roomId}")
    public ResponseEntity<RegisterResponse> register(
            @PathVariable("roomId") String roomId,
            @RequestBody RegisterRequest request
    ) {
        RegisterResponse response = voterService.register(roomId, request.name(), request.password());
        return ResponseEntity.ok(response);
    }
}
