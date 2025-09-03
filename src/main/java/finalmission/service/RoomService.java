package finalmission.service;

import finalmission.domain.Room;
import finalmission.dto.MakingRoomRequest;
import finalmission.repository.RoomRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room createRoom(MakingRoomRequest makingRoomRequest) {
        Room room = new Room(null, makingRoomRequest.roomName(), makingRoomRequest.seatsCount());
        Room savedRoom = roomRepository.save(room);
        return savedRoom;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
}
