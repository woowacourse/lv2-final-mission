package finalmission.service;

import finalmission.controller.RoomCreateRequest;
import finalmission.controller.RoomCreateResponse;
import finalmission.domain.Member;
import finalmission.domain.Room;
import finalmission.domain.RoomMember;
import finalmission.repository.RoomMemberRepository;
import finalmission.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final MemberService memberService;
    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;

    @Transactional
    public RoomCreateResponse create(final RoomCreateRequest request) {
        final Member manager = memberService.getById(request.memberId());

        final Room room = roomRepository.save(request.toRoom(manager));
        roomMemberRepository.save(new RoomMember(room, manager));

        return RoomCreateResponse.from(room);
    }
}
