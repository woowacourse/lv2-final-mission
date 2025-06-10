package finalmission.controller;

import finalmission.domain.Voter;
import finalmission.dto.request.VoteRequest;
import finalmission.dto.response.VoteResponses;
import finalmission.dto.response.VoteStaticsResponses;
import finalmission.service.VoteService;
import finalmission.service.VoterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VoteController {

    private final VoterService voterService;
    private final VoteService voteService;

    @PostMapping("/time/{roomId}")
    public void addTime(
            @PathVariable("roomId") String roomId,
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            @RequestBody VoteRequest request
    ) {
        Voter voter = voterService.validateAndGet(name, password);
        voteService.vote(roomId, voter, request.values());
    }

    @GetMapping("/time/{roomId}")
    public VoteStaticsResponses getTimeStatics(
            @PathVariable("roomId") String roomId
    ) {
        return voteService.getVoteStatics(roomId);
    }

    @GetMapping("/time/{roomId}/my")
    public VoteResponses getMyTimes(
            @PathVariable("roomId") String roomId,
            @RequestParam("name") String name,
            @RequestParam("password") String password
    ) {
        Voter voter = voterService.validateAndGet(name, password);
        return voteService.getMyVotes(roomId, voter);
    }

    @DeleteMapping("/time/{roomId}")
    public void deleteAllTimeOf(
            @PathVariable("roomId") String roomId,
            @RequestParam("name") String name,
            @RequestParam("password") String password
    ) {
        Voter voter = voterService.validateAndGet(name, password);
        voteService.dropMyVotes(roomId, voter);
    }
}
