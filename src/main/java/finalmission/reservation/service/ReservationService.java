package finalmission.reservation.service;

import finalmission.common.exception.DuplicatedException;
import finalmission.common.exception.ForbiddenException;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberInfo;
import finalmission.member.repository.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.MyReservationResponse;
import finalmission.reservation.dto.ReservationCreateRequest;
import finalmission.reservation.dto.ReservationCreateResponse;
import finalmission.reservation.dto.ReservationDeleteRequest;
import finalmission.reservation.dto.ReservationModifyRequest;
import finalmission.reservation.dto.ReservationModifyResponse;
import finalmission.reservation.dto.ReservationSearchRequest;
import finalmission.reservation.dto.SearchReservationResponse;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.room.domain.Room;
import finalmission.room.repository.RoomRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;

    public ReservationService(final ReservationRepository reservationRepository,
                              final MemberRepository memberRepository,
                              final RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.roomRepository = roomRepository;
    }

    public ReservationCreateResponse createReservation(final MemberInfo memberInfo, final ReservationCreateRequest request) {
        Member member = getMember(memberInfo.memberId());
        Room room = getRoom(request.roomId());
        Reservation reservation = new Reservation(request.time(), request.description(), member, room);
        Reservation savedReservation = reservationRepository.save(reservation);
        return new ReservationCreateResponse(savedReservation.getId());
    }

    public ReservationModifyResponse modifyReservation(final MemberInfo memberInfo,
                                                       final ReservationModifyRequest request) {
        validateReservationOwner(request.reservationId(), memberInfo.memberId());
        Reservation previousReservation = getReservation(request.reservationId());
        reservationRepository.delete(previousReservation);

        if (reservationRepository.existsByTimeAndRoomId(request.time(), request.roomId())) {
            throw new DuplicatedException("예약이 이미 존재합니다.");
        }
        Reservation reservation = new Reservation(request.time(), request.description(),
                getMember(memberInfo.memberId()),
                getRoom(request.roomId()));
        Reservation savedReservation = reservationRepository.save(reservation);
        return new ReservationModifyResponse(savedReservation.getId(), reservation.getTime());

    }

    public void deleteReservation(final MemberInfo memberInfo,
                                  final ReservationDeleteRequest request) {

        Long reservationId = request.reservationId();
        validateReservationOwner(reservationId, memberInfo.memberId());
        reservationRepository.deleteById(reservationId);
    }

    public MyReservationResponse findMyReservations(final Long memberId) {
        List<Reservation> reservations = reservationRepository.findByMemberId(memberId);
        return MyReservationResponse.from(reservations);
    }

    public SearchReservationResponse searchReservations(final ReservationSearchRequest request) {
        LocalDateTime startTime = request.date().atStartOfDay();
        LocalDateTime endTime = LocalDateTime.of(request.date(), LocalTime.MAX);
        List<Reservation> reservations = reservationRepository.findByTimeBetweenAndRoomId(startTime, endTime,
                request.roomId());
        return SearchReservationResponse.from(reservations);
    }

    private void validateReservationOwner(final Long reservationId, final Long memberId) {
        if (!reservationRepository.existsByIdAndMemberId(reservationId, memberId)) {
            throw new ForbiddenException("자신의 예약이 아닙니다.");
        }
    }

    private Reservation getReservation(final Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
    }

    private Room getRoom(final Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("방을 찾을 수 없습니다."));
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다."));
    }
}
