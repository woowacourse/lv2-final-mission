package finalmission.service;

import finalmission.domain.Room;
import finalmission.dto.RoomCreateRequest;
import finalmission.dto.RoomResponse;
import finalmission.exception.BadRequestException;
import finalmission.exception.ErrorCode;
import finalmission.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomResponse addRoom(final RoomCreateRequest roomCreateRequest) {
        if (roomRepository.existsByName(roomCreateRequest.name())) {
            throw new BadRequestException(ErrorCode.DUPLICATE_ROOM_NAME);
        }
        final Room newRoom = new Room(roomCreateRequest.name(), roomCreateRequest.maxNumberOfPeople());
        return RoomResponse.from(roomRepository.save(newRoom));
    }
}
