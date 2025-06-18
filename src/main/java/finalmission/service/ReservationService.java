package finalmission.service;

import finalmission.dto.request.CreateReservationRequest;
import finalmission.dto.request.MemberInfo;
import finalmission.dto.request.MyReservationResponse;
import finalmission.dto.response.CreateReservationResponse;
import finalmission.dto.response.ReservationResponse;
import finalmission.entity.BlackList;
import finalmission.entity.MeetingRoom;
import finalmission.entity.Member;
import finalmission.entity.Reservation;
import finalmission.entity.ReservationTime;
import finalmission.exception.custom.CannotAccessException;
import finalmission.exception.custom.CannotRemoveException;
import finalmission.exception.custom.DuplicatedValueException;
import finalmission.exception.custom.NotAuthenticatedException;
import finalmission.exception.custom.NotExistedValueException;
import finalmission.repository.BlackListRepository;
import finalmission.repository.MeetingRoomRepository;
import finalmission.repository.MemberRepository;
import finalmission.repository.ReservationRepository;
import finalmission.repository.ReservationTimeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final BlackListRepository blackListRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final MeetingRoomRepository meetingRoomRepository;

    public ReservationService(final ReservationRepository reservationRepository,
                              final MemberRepository memberRepository,
                              final BlackListRepository blackListRepository,
                              final ReservationTimeRepository reservationTimeRepository,
                              final MeetingRoomRepository meetingRoomRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.blackListRepository = blackListRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.meetingRoomRepository = meetingRoomRepository;
    }

    public List<ReservationResponse> findAllReservation() {
        return reservationRepository.findAllFetch()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public List<MyReservationResponse> findAllMyReservation(final MemberInfo memberInfo) {
        return reservationRepository.findAllByMemberId(memberInfo.id())
                .stream()
                .map(MyReservationResponse::from)
                .toList();
    }

    public CreateReservationResponse addReservation(final CreateReservationRequest request,
                                                    final MemberInfo memberInfo) {

        Member member = memberRepository.findById(memberInfo.id())
                .orElseThrow(() -> new NotAuthenticatedException("존재하지 않는 유저입니다."));

        checkBannedMember(member);

        MeetingRoom meetingRoom = meetingRoomRepository.findById(request.meetingRoomId())
                .orElseThrow(() -> new NotExistedValueException("존재하지 않는 회의실입니다."));
        ReservationTime reservationTime = reservationTimeRepository.findById(request.timeId())
                .orElseThrow(() -> new NotExistedValueException("존재하지 않는 예약 가능 시간입니다."));

        if (reservationRepository.existsByMeetingRoomIdAndDateAndReservationTimeId(meetingRoom.getId(),
                request.date(),
                reservationTime.getId())) {
            throw new DuplicatedValueException("이미 예약이 존재합니다.");
        }

        Reservation reservation = new Reservation(member, request.date(), reservationTime, meetingRoom);
        Reservation saved = reservationRepository.save(reservation);
        return CreateReservationResponse.from(saved);
    }

    private void checkBannedMember(final Member member) {
        Optional<BlackList> blackListOptional = blackListRepository.findByMember(member);

        if (blackListOptional.isPresent()) {
            BlackList blackList = blackListOptional.get();
            throw new CannotAccessException("관리자에 의해 제한된 유저입니다. - 시작일: %s, 사유: %s"
                    .formatted(blackList.getBannedSince(), blackList.getReason()));
        }
    }

    public void deleteReservation(final Long id, final MemberInfo memberInfo) {
        if (!reservationRepository.existsById(id)) {
            throw new NotExistedValueException("존재하지 않는 예약입니다.");
        }
        if (!reservationRepository.existsByIdAndMemberId(id, memberInfo.id())) {
            throw new CannotRemoveException("예약자만 삭제할 수 있습니다.");
        }
        reservationRepository.deleteById(id);
    }
}
