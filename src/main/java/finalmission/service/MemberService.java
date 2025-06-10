package finalmission.service;

import finalmission.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    public void register(String roomId, String name, String password) {
        Room room = roomRepository.findById(new Id(roomId)).orElseThrow();
        if (room.containsNameOf(name)) {
            throw new IllegalArgumentException("이미 해당 이름으로 등록된 사용자가 존재합니다.");
        }
        Member member = new Member(name, password, room);
        memberRepository.save(member);
    }
}
