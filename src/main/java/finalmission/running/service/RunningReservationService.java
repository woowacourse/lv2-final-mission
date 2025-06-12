package finalmission.running.service;

import finalmission.member.domain.Member;
import finalmission.member.dto.response.LoginInfo;
import finalmission.member.exception.UnauthorizedException;
import finalmission.member.repository.MemberRepository;
import finalmission.running.domain.RunningSession;
import finalmission.running.dto.request.ReservationRequest;
import finalmission.running.dto.response.ReservationResponse;
import finalmission.running.repository.RunningReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RunningReservationService {

    private final RunningReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ReservationResponse createRunningReservation(ReservationRequest request, LoginInfo loginInfo) {
        Member creator = memberRepository.findById(loginInfo.id())
            .orElseThrow(() -> new UnauthorizedException("사용자를 찾을 수 없습니다."));

        RunningSession runningSession = RunningSession.createWithoutId(
            creator,
            request.date(),
            request.startAt(),
            request.endTime()
        );

        RunningSession save = reservationRepository.save(runningSession);

        return new ReservationResponse(
            save.getId(),
            save.getCreator().getNickname(),
            save.getDate(),
            save.getStartAt(),
            save.getEndTime()
        );
    }
}
