package finalmission.service;

import finalmission.domain.Id;
import finalmission.domain.Room;
import finalmission.domain.RoomRepository;
import finalmission.dto.response.RoomCreateResponse;
import finalmission.dto.response.RoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public RoomCreateResponse createRoom(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, boolean anonymousRoom) {
        Room room = new Room(startDate, endDate, startTime, endTime, anonymousRoom);
        roomRepository.save(room);
        return RoomCreateResponse.from(room);
    }

    @Transactional(readOnly = true)
    public RoomResponse getRoomInfo(String roomId) {
        Room room = roomRepository.findById(new Id(roomId)).orElseThrow();

        return RoomResponse.from(room);
    }
}
