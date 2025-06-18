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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        validateStartDateAndTimeNotFuture(request.startDate(), request.startTime());
        final Room room = roomRepository.save(request.toRoom(manager));
        final RoomMember roomMember = roomMemberRepository.save(new RoomMember(room, manager));
        room.addRoomMember(roomMember);

        return RoomCreateResponse.from(room);
    }

    private void validateStartDateAndTimeNotFuture(final LocalDate startDate, final LocalTime startTime) {
        final LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);

        if (startDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("과거의 날짜 및 시간으로 내전방을 생성할 수 없습니다.");
        }
    }

    public RoomResponse getById(final Long id) {
        final Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 내전방을 찾을 수 없습니다. id: " + id));

        return RoomResponse.from(room);
    }

    public List<RoomWithoutParticipantsResponse> findByMemberId(final Long memberId) {
        final Member member = memberService.getById(memberId);
        final List<RoomMember> roomMembers = roomMemberRepository.findByMemberId(member.getId());

        return roomMembers.stream()
                .map(RoomMember::getRoom)
                .map(RoomWithoutParticipantsResponse::from)
                .toList();
    }

    public List<RoomWithoutParticipantsResponse> findAll() {
        final List<Room> rooms = roomRepository.findAll();

        return rooms.stream()
                .map(RoomWithoutParticipantsResponse::from)
                .toList();
    }

    @Transactional
    public void join(final Long roomId, final Long memberId) {
        final Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("해당 id의 내전방을 찾을 수 없습니다. id: " + roomId));
        validateGameAlreadyStart(room);

        final Member member = memberService.getById(memberId);

        validateParticipantsSize(room);
        validateAlreadyJoin(room, member);

        final RoomMember roomMember = roomMemberRepository.save(new RoomMember(room, member));
        room.addRoomMember(roomMember);
    }

    private void validateGameAlreadyStart(final Room room) {
        if (room.isGameStarted()) {
            throw new IllegalArgumentException("이미 시작한 내전입니다.");
        }
    }

    private void validateParticipantsSize(final Room room) {
        if (room.isFull()) {
            throw new IllegalArgumentException("이미 방이 꽉 찼습니다.");
        }
    }

    private void validateAlreadyJoin(final Room room, final Member member) {
        if (room.isJoined(member)) {
            throw new IllegalStateException("이미 해당 내전방에 참여한 유저입니다.");
        }
    }

    @Transactional
    public void leave(final Long roomId, final Long memberId) {
        final Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("해당 id의 내전방을 찾을 수 없습니다. id: " + roomId));
        final Member member = memberService.getById(memberId);

        final RoomMember roomMember = roomMemberRepository.findByRoomIdAndMemberId(room.getId(), member.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 내전방에 참여하지 않은 유저입니다."));

        roomMemberRepository.delete(roomMember);
    }
}
