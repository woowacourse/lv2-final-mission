package finalmission.room.service;

import finalmission.global.error.exception.NotFoundException;
import finalmission.room.dto.request.RoomRequest;
import finalmission.room.dto.response.RoomResponse;
import finalmission.room.entity.Room;
import finalmission.room.repository.RoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public RoomResponse createRoom(RoomRequest request) {
        Room room = request.toEntity();
        Room saved = roomRepository.save(room);
        return RoomResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<RoomResponse> findAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoomResponse findRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 회의실입니다."));

        return RoomResponse.from(room);
    }

    @Transactional
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
