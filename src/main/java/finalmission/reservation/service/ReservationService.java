package finalmission.reservation.service;

import finalmission.member.domain.Member;
import finalmission.member.dto.MemberResponse;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.ReservationRequest;
import finalmission.reservation.dto.ReservationResponse;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.room.domain.Room;
import finalmission.room.dto.RoomResponse;
import finalmission.room.repository.RoomRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public ReservationResponse addReservation(ReservationRequest request, Member member) {
        Room room = getRoomById(request.roomId());
        Reservation reservation = new Reservation(null, request.date(), request.time(), room, member);
        Reservation newReservation = reservationRepository.save(reservation);
        return new ReservationResponse(new MemberResponse(newReservation.getMember().getName()), new RoomResponse(newReservation.getRoom().getName(), newReservation.getRoom().getCapacity()), newReservation.getDate(), newReservation.getTime());
    }

    private Room getRoomById(Long id) {
        return roomRepository.findById(id).orElseThrow();
    }

    public List<ReservationResponse> getAll() {
        return reservationRepository.findAll().stream()
                .map(reservation -> new ReservationResponse(new MemberResponse(reservation.getMember().getName()), new RoomResponse(reservation.getRoom().getName(), reservation.getRoom().getCapacity()), reservation.getDate(), reservation.getTime()))
                .toList();
    }

    @Transactional
    public void deleteReservation(Long id, Long memberId) {
        Optional<Reservation> reservation = reservationRepository.findById(id);

        if (reservation.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 예약 id입니다.");
        }
        if (!Objects.equals(reservation.get().getMember().getId(), memberId)) {
            throw new IllegalArgumentException("해당 예약을 삭제할 권한이 없습니다.");
        }

        reservationRepository.deleteById(id);
    }
}
