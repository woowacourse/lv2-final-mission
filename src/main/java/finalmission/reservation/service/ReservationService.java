package finalmission.reservation.service;

import finalmission.global.error.exception.BadRequestException;
import finalmission.global.error.exception.ForbiddenException;
import finalmission.global.error.exception.NotFoundException;
import finalmission.member.entity.Member;
import finalmission.member.repository.MemberRepository;
import finalmission.reservation.dto.request.ReservationCreateRequest;
import finalmission.reservation.dto.request.ReservationUpdateRequest;
import finalmission.reservation.dto.response.ReservationResponse;
import finalmission.reservation.entity.Reservation;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.room.entity.Room;
import finalmission.room.repository.RoomRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public ReservationResponse createReservation(ReservationCreateRequest request) {
        // TODO: 겹치는 시간, 날짜 확인 확인
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new BadRequestException("존재하지 않는 멤버입니다."));
        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 회의실입니다."));

        Reservation reservation = new Reservation(
                member,
                room,
                request.date(),
                request.startTime(),
                request.endTime(),
                request.purpose()
        );
        reservationRepository.save(reservation);

        return ReservationResponse.from(reservation);
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> findAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> findAllMyReservation(Long id) {
        return reservationRepository.findAllByMemberId(id).stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReservationResponse findReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 예약입니다."));

        return ReservationResponse.from(reservation);
    }

    @Transactional
    public void updateReservation(Long reservationId, Long memberId, ReservationUpdateRequest request) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 예약입니다."));

        validateOwnReservation(reservation, memberId);

        reservation.update(request.purpose());
    }

    @Transactional
    public void deleteReservation(Long reservationId, Long memberId) {
        // TODO: Optional 개선
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isEmpty()) {
            return;
        }

        validateOwnReservation(reservation.get(), memberId);

        reservationRepository.deleteById(reservationId);
    }

    private void validateOwnReservation(Reservation reservation, Long memberId) {
        if (!reservation.isMine(memberId)) {
            throw new ForbiddenException("권한이 없습니다.");
        }
    }
}
