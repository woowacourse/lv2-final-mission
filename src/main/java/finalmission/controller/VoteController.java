package finalmission.controller;

import finalmission.domain.Voter;
import finalmission.dto.request.VoteRequest;
import finalmission.dto.response.VoteResponses;
import finalmission.dto.response.VoteStaticsResponses;
import finalmission.service.VoteService;
import finalmission.service.VoterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class VoteController {

    private final VoterService voterService;
    private final VoteService voteService;

    @PostMapping("/vote/{roomId}")
    public ResponseEntity<Void> vote(
            @PathVariable("roomId") String roomId,
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            @RequestBody VoteRequest request
    ) {
        Voter voter = voterService.validateAndGet(name, password);
        voteService.vote(roomId, voter, request.values());
        URI createdLocation = URI.create("/time/" + roomId + "/my");
        return ResponseEntity.created(createdLocation).build();
    }

    @GetMapping("/vote/{roomId}")
    public ResponseEntity<VoteStaticsResponses> getVoteStatics(
            @PathVariable("roomId") String roomId
    ) {
        VoteStaticsResponses response = voteService.getVoteStatics(roomId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vote/{roomId}/my")
    public ResponseEntity<VoteResponses> getMyVotes(
            @PathVariable("roomId") String roomId,
            @RequestParam("name") String name,
            @RequestParam("password") String password
    ) {
        Voter voter = voterService.validateAndGet(name, password);
        VoteResponses response = voteService.getMyVotes(roomId, voter);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/vote/{roomId}")
    public ResponseEntity<Void> dropMyVotes(
            @PathVariable("roomId") String roomId,
            @RequestParam("name") String name,
            @RequestParam("password") String password
    ) {
        Voter voter = voterService.validateAndGet(name, password);
        voteService.dropMyVotes(roomId, voter);
        return ResponseEntity.noContent().build();
    }
}
