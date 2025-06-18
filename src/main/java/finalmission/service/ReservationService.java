package finalmission.service;

import static finalmission.global.exception.ErrorMessage.INTERNAL_SERVER_ERROR;

import finalmission.controller.dto.ReservationDetailResponse;
import finalmission.controller.dto.ReservationRequest;
import finalmission.controller.dto.ReservationResponse;
import finalmission.controller.dto.ReservationResponses;
import finalmission.controller.dto.ReservationUpdateRequest;
import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.domain.Room;
import finalmission.global.config.ExecutionTimeAnnotation;
import finalmission.infrastructure.MailGunClient;
import finalmission.repository.ReservationRepository;
import finalmission.repository.RoomRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final MailGunClient mailClient;

    @ExecutionTimeAnnotation
    public ReservationResponses getAllReservation() {
        List<ReservationResponse> responses = reservationRepository.findAll().stream()
                .map(ReservationResponse::new)
                .toList();
        return new ReservationResponses(responses);
    }

    public ReservationDetailResponse getDetailReservationById(Long reservationId, Member member) {
        Reservation reservation = getReservationById(reservationId);

        String errorMessage = "다른 사용자 예약을 상세 열람할 수 없습니다.";
        validateDifferentMember(reservation.getMember(), member, errorMessage);
        return new ReservationDetailResponse(reservation);
    }

    public ReservationResponse registerReservation(Member member, ReservationRequest request) {
        Room room = getRoomById(request.roomId());
        Reservation reservation = request.toReservation(member, room);

        validateReservationLimit(member.getId(), reservation.getDate());
        validateDuplicateTime(request.date(), request.startTime(), request.endTime());

        reservationRepository.save(reservation);

        mailClient.sendReservationMail(reservation);

        log.info("[예약 등록 성공] 회의실 예약 등록 성공 reservationId: {}", reservation.getId());
        return new ReservationResponse(reservation);
    }

    @ExecutionTimeAnnotation
    public ReservationResponse updateReservation(
            Long reservationId,
            ReservationUpdateRequest request,
            Member member
    ) {
        Reservation reservation = getReservationById(reservationId);

        validateUpdate(request, member, reservation);

        Room room = getRoomById(request.roomId());
        reservation.update(room, request.date(), request.startTime(), request.endTime());
        log.info("[예약 수정 성공] 회의실 예약 수정 성공 reservationId: {}", reservation.getId());
        return new ReservationResponse(reservation);
    }

    public void deleteReservation(Long reservationId, Member member) {
        Reservation reservation = getReservationById(reservationId);

        String errorMessage = "다른 사용자 예약을 삭제할 수 없습니다.";
        validateDifferentMember(reservation.getMember(), member, errorMessage);

        reservationRepository.deleteById(reservationId);
        log.info("[예약 삭제 성공] 회의실 예약 삭제 성공 reservationId: {}", reservation.getId());
    }

    private void validateUpdate(ReservationUpdateRequest request, Member member, Reservation reservation) {
        String errorMessage = "다른 사용자 예약을 수정할 수 없습니다.";

        if (!reservation.matchDate(request.date())) {
            validateReservationLimit(member.getId(), request.date());
        }

        validateDifferentMember(reservation.getMember(), member, errorMessage);

        if (!reservation.isDuplicateTime(request.startTime(), request.endTime())) {
            validateDuplicateTime(request.date(), request.startTime(), request.endTime());
        }
    }

    private void validateReservationLimit(Long memberId, LocalDate date) {
        List<Reservation> reservations = reservationRepository.findAllByMemberIdAndDate(memberId, date);

        if (reservations.size() >= 2) {
            throw new IllegalArgumentException("같은 날 3개 이상 회의실을 예약할 수 없습니다.");
        }
    }

    private void validateDuplicateTime(LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Reservation> reservations = reservationRepository.findAllByDate(date);
        boolean isDuplicate = reservations.stream()
                .anyMatch(reservation -> reservation.isDuplicateTime(startTime, endTime));

        if (isDuplicate) {
            throw new IllegalArgumentException("이미 예약된 시간입니다. 다른 시간을 이용해 주세요.");
        }
    }

    private Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> {
                    log.error("[예약 서비스] 존재하지 않는 예약 조회 - reservationId: {}", reservationId);
                    return new RuntimeException(INTERNAL_SERVER_ERROR.get());
                });
    }

    private void validateDifferentMember(Member reservationMember, Member loginMember, String errorMessage) {
        if (!reservationMember.equals(loginMember)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> {
                    log.error("[예약 서비스] 존재하지 않는 방 조회 - roomId: {}", roomId);
                    return new RuntimeException(INTERNAL_SERVER_ERROR.get());
                });
    }
}
