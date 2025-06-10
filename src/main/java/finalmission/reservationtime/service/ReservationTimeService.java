package finalmission.reservationtime.service;

import finalmission.meetingroom.domain.MeetingRoom;
import finalmission.meetingroom.repository.MeetingRoomRepository;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.reservationtime.domain.ReservationTime;
import finalmission.reservationtime.dto.ReservationTimeAvailableRequest;
import finalmission.reservationtime.dto.ReservationTimeAvailableResponse;
import finalmission.reservationtime.dto.ReservationTimeRequest;
import finalmission.reservationtime.dto.ReservationTimeResponse;
import finalmission.reservationtime.repository.ReservationTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {

    private final ReservationTimeRepository timeRepository;
    private final MeetingRoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public ReservationTimeResponse createTime(ReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(request.startAt());
        ReservationTime savedReservationTime = timeRepository.save(reservationTime);
        return new ReservationTimeResponse(savedReservationTime.getId(), savedReservationTime.getStartAt());
    }

    public List<ReservationTimeResponse> getAllTimes() {
        return timeRepository.findAll().stream()
                .map(time -> new ReservationTimeResponse(time.getId(), time.getStartAt()))
                .toList();
    }

    public List<ReservationTimeAvailableResponse> findAvailableTimes(ReservationTimeAvailableRequest request) {
        MeetingRoom meetingRoom = getMeetingRoom(request.roomId());
        return reservationRepository.findAvailableTimes(request.date(), meetingRoom);
    }

    private MeetingRoom getMeetingRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("해당 회의실은 존재하지 않습니다."));
    }
}
