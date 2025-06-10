package finalmission.service;

import finalmission.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoterService {

    private final NameGenerator nameGenerator;
    private final RoomRepository roomRepository;
    private final VoterRepository voterRepository;

    @Transactional
    public void register(String roomId, String name, String password) {
        Room room = roomRepository.findById(new Id(roomId)).orElseThrow();
        if (room.containsNameOf(name)) {
            throw new IllegalArgumentException("이미 해당 이름으로 등록된 투표자가 존재합니다.");
        }
        String newName = name == null ? nameGenerator.generate() : name;
        Voter voter = new Voter(newName, password, room);
        voterRepository.save(voter);
    }

    @Transactional
    public Voter validateAndGet(String name, String password) {
        if (!voterRepository.existsByNameAndPassword(name, password)) {
            throw new IllegalArgumentException("존재하지 않는 투표자입니다.");
        }
        Voter voter = voterRepository.findByName(name).orElseThrow();
        voter.validatePassword(password);
        return voter;
    }
}
