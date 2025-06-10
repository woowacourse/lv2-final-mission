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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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
}
