package finalmission.application;

import finalmission.domain.Reservation;
import finalmission.domain.YogaSession;
import finalmission.domain.YogaSessionForBooking;
import finalmission.domain.repository.MemberRepository;
import finalmission.domain.repository.ReservationRepository;
import finalmission.domain.repository.YogaSessionRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final YogaSessionRepository yogaSessionRepository;
    private final MemberRepository memberRepository;

    public List<YogaSessionForBooking> getYogaSessionsForBooking(LocalDate date) {
        var sessions = yogaSessionRepository.findAllByDate(date);
        return sessions.stream()
                .map(session -> YogaSessionForBooking.of(session, getCountBySession(session)))
                .toList();
    }

    public Reservation register(final long memberId, final long sessionId) {
        var session = yogaSessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 세션입니다. id: " + sessionId));
        validateSessionAttendance(session);

        var member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. id: " + memberId));

        var reservation = new Reservation(member, session);
        return reservationRepository.save(reservation);
    }

    private void validateSessionAttendance(final YogaSession session) {
        var attendance = getCountBySession(session);
        if (session.isOverCapacity(attendance + 1)) {
            throw new IllegalArgumentException("정원 초과로 예약할 수 없습니다.");
        }
    }

    private long getCountBySession(final YogaSession session) {
        return reservationRepository.countBySession(session);
    }

    public List<Reservation> getMyReservations(final long memberId) {
        return reservationRepository.findAllByMemberId(memberId);
    }

    public void cancel(final Long reservationId, final Long memberId) {
        var reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다. id: " + reservationId));

        if (!reservation.isOwner(memberId)) {
            throw new IllegalArgumentException(
                    String.format("해당 예약의 삭제 권한이 없는 사용자입니다. 예약 id: %d, 사용자 id: %d", reservationId, memberId)
            );
        }

        reservationRepository.deleteById(reservationId);
    }
}
