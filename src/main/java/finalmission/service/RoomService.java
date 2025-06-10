package finalmission.service;

import finalmission.controller.dto.RoomCreateRequest;
import finalmission.controller.dto.RoomCreateResponse;
import finalmission.controller.dto.RoomResponse;
import finalmission.controller.dto.RoomWithoutParticipantsResponse;
import finalmission.domain.Member;
import finalmission.domain.Room;
import finalmission.domain.RoomMember;
import finalmission.exception.NotFoundException;
import finalmission.repository.RoomMemberRepository;
import finalmission.repository.RoomRepository;
import jakarta.transaction.Transactional;
import java.util.List;
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
        final RoomMember roomMember = roomMemberRepository.save(new RoomMember(room, manager));
        room.addRoomMember(roomMember);

        return RoomCreateResponse.from(room);
    }

    public RoomResponse getById(final Long id) {
        final Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 내전방을 찾을 수 없습니다. id: " + id));

        return RoomResponse.from(room);
    }

    public List<RoomWithoutParticipantsResponse> findAll() {
        final List<Room> rooms = roomRepository.findAll();

        return rooms.stream()
                .map(RoomWithoutParticipantsResponse::from)
                .toList();
    }
}
