package finalmission.service;

import finalmission.domain.*;
import finalmission.dto.response.VoteResponses;
import finalmission.dto.response.VoteStaticsResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final RoomRepository roomRepository;
    private final VoteRepository voteRepository;

    @Transactional
    public void vote(String roomId, Voter voter, List<LocalDateTime> values) {
        Room room = roomRepository.findById(new Id(roomId)).orElseThrow();

        List<Vote> createdVotes = values.stream()
                .map(dateTime -> room.createVote(voter, dateTime))
                .toList();
        voteRepository.saveAll(createdVotes);
    }

    @Transactional(readOnly = true)
    public VoteStaticsResponses getVoteStatics(String roomId) {
        List<Vote> votes = voteRepository.getTimesByRoom_Id(new Id(roomId));
        List<VoteStatics> statics = Vote.calculateStatics(votes);
        return VoteStaticsResponses.from(statics);
    }

    @Transactional(readOnly = true)
    public VoteResponses getMyVotes(String roomId, Voter voter) {
        Room room = roomRepository.findById(new Id(roomId)).orElseThrow();
        List<Vote> votes = room.getTimesOf(voter);
        List<LocalDateTime> dateTimes = votes.stream()
                .map(Vote::getDateTime)
                .toList();
        return VoteResponses.from(voter.getName(), dateTimes);
    }

    @Transactional
    public void dropMyVotes(String roomId, Voter voter) {
        voteRepository.deleteAllByRoom_IdAndVoter(new Id(roomId), voter);
    }
}
