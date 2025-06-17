package finalmission.reservation.service;

import finalmission.email.service.EmailService;
import finalmission.member.domain.Member;
import finalmission.member.dto.MemberResponse;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.EditRequest;
import finalmission.reservation.dto.MyReservationResponse;
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
    private final EmailService emailService;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository, EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.emailService = emailService;
    }

    @Transactional
    public ReservationResponse addReservation(ReservationRequest request, Member member) {
        Room room = getRoomById(request.roomId());
        Reservation reservation = new Reservation(null, request.date(), request.time(), request.description(), room, member);
        Reservation newReservation = reservationRepository.save(reservation);
        ReservationResponse response = new ReservationResponse(new RoomResponse(newReservation.getRoom().getName(), newReservation.getRoom().getCapacity()), newReservation.getDate(), newReservation.getTime());

        emailService.sendEmail(member.getEmail(), response);

        return response;
    }

    private Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방 id입니다."));
    }

    public List<ReservationResponse> getAll() {
        return reservationRepository.findAll().stream()
                .map(reservation -> new ReservationResponse(new RoomResponse(reservation.getRoom().getName(), reservation.getRoom().getCapacity()), reservation.getDate(), reservation.getTime()))
                .toList();
    }

    public MyReservationResponse getMemberReservation(Long id, Long memberId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);

        if (optionalReservation.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 예약 id입니다.");
        }
        if (!Objects.equals(optionalReservation.get().getMember().getId(), memberId)) {
            throw new IllegalArgumentException("내 예약이 아닙니다.");
        }

        Reservation reservation = optionalReservation.get();

        return new MyReservationResponse(new RoomResponse(reservation.getRoom().getName(), reservation.getRoom().getCapacity()), reservation.getDate(), reservation.getTime(), reservation.getDescription(), new MemberResponse(reservation.getMember().getName()));
    }

    @Transactional
    public MyReservationResponse editReservation(Long id, EditRequest request, Long memberId) {
        Optional<Reservation> reservation = reservationRepository.findById(id);

        if (reservation.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 예약 id입니다.");
        }
        if (!Objects.equals(reservation.get().getMember().getId(), memberId)) {
            throw new IllegalArgumentException("해당 예약을 수정할 권한이 없습니다.");
        }

        Reservation oldReservation = reservation.get();
        Room room = getRoomById(request.roomId());

        Reservation newReservation = reservationRepository.save(new Reservation(oldReservation.getId(), request.date(), request.time(), request.description(), room, oldReservation.getMember()));

        return new MyReservationResponse(new RoomResponse(newReservation.getRoom().getName(), newReservation.getRoom().getCapacity()), newReservation.getDate(), newReservation.getTime(), newReservation.getDescription(), new MemberResponse(newReservation.getMember().getName()));
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
