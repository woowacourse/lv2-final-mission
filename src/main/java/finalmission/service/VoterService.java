package finalmission.service;

import finalmission.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoterService {

    private final RoomRepository roomRepository;
    private final VoterRepository voterRepository;

    @Transactional
    public void register(String roomId, String name, String password) {
        Room room = roomRepository.findById(new Id(roomId)).orElseThrow();
        if (room.containsNameOf(name)) {
            throw new IllegalArgumentException("이미 해당 이름으로 등록된 투표자가 존재합니다.");
        }
        Voter voter = new Voter(name, password, room);
        voterRepository.save(voter);
    }
}
