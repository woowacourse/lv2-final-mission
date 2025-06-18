package finalmission.service;

import finalmission.dto.request.CreateMeetingRoomRequest;
import finalmission.dto.response.CreateMeetingRoomResponse;
import finalmission.dto.response.MeetingRoomResponse;
import finalmission.entity.MeetingRoom;
import finalmission.exception.custom.CannotRemoveException;
import finalmission.exception.custom.DuplicatedValueException;
import finalmission.exception.custom.InvalidValueException;
import finalmission.exception.custom.NotExistedValueException;
import finalmission.repository.MeetingRoomRepository;
import finalmission.repository.ReservationRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MeetingRoomService {

    private final MeetingRoomRepository meetingRoomRepository;
    private final ReservationRepository reservationRepository;

    public MeetingRoomService(final MeetingRoomRepository meetingRoomRepository,
                              final ReservationRepository reservationRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<MeetingRoomResponse> findAllMeetingRoom() {
        return meetingRoomRepository.findAll()
                .stream()
                .map(MeetingRoomResponse::from)
                .toList();
    }

    public CreateMeetingRoomResponse addMeetingRoom(final CreateMeetingRoomRequest request) {
        if (request.availablePeopleCount() <= 0) {
            throw new InvalidValueException("가용 인원은 0보다 커야 합니다.");
        }
        if (meetingRoomRepository.existsByName(request.name())) {
            throw new DuplicatedValueException("중복된 회의실 명입니다.");
        }
        MeetingRoom meetingRoom = new MeetingRoom(request.name(), request.describe(), request.availablePeopleCount());
        MeetingRoom saved = meetingRoomRepository.save(meetingRoom);

        return CreateMeetingRoomResponse.from(saved);
    }

    public void deleteMeetingRoom(final Long id) {
        if (reservationRepository.existsByMeetingRoomId(id)) {
            throw new CannotRemoveException("예약이 존재하는 회의실은 제거할 수 없습니다.");
        }
        if (!meetingRoomRepository.existsById(id)) {
            throw new NotExistedValueException("존재하지 않는 회의실입니다.");
        }
        meetingRoomRepository.deleteById(id);
    }
}
