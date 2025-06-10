package finalmission.application.service;

import finalmission.application.dto.ReservationDateTimeRequest;
import finalmission.application.dto.ReservationDateTimeResponse;
import finalmission.domain.Coach;
import finalmission.domain.ReservationDateTime;
import finalmission.domain.repository.CoachRepository;
import finalmission.domain.repository.ReservationDateTimeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationDateTimeService {
    private final ReservationDateTimeRepository reservationDateTimeRepository;
    private final CoachRepository coachRepository;

    public ReservationDateTimeService(final ReservationDateTimeRepository reservationDateTimeRepository,
                                      final CoachRepository coachRepository) {
        this.reservationDateTimeRepository = reservationDateTimeRepository;
        this.coachRepository = coachRepository;
    }

    public Long createReservationDateTime(final ReservationDateTimeRequest reservationDateTimeRequest) {
        Coach coach = coachRepository.findById(reservationDateTimeRequest.getCoachId())
                .orElseThrow(() -> new IllegalArgumentException("코치가 존재하지 않습니다"));

        final ReservationDateTime reservationDateTime = reservationDateTimeRepository.save(new ReservationDateTime(
                reservationDateTimeRequest.getDateTime(),
                coach
        ));

        return reservationDateTime.getId();
    }

    public List<ReservationDateTimeResponse> getReservationDateTimes(final String nickname) {
        Coach coach = coachRepository.findByMemberNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("코치가 존재하지 않습니다"));

        return reservationDateTimeRepository.findByCoach(coach).stream()
                .map(ReservationDateTimeResponse::new)
                .toList();
    }

    public void deleteReservationDateTime(final Long id, final Long coachId) {
        coachRepository.findById(coachId)
                .orElseThrow(() -> new IllegalArgumentException("코치가 존재하지 않습니다"));

        ReservationDateTime reservationDateTime = reservationDateTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("시간이 존재하지 않습니다"));

        reservationDateTimeRepository.delete(reservationDateTime);
    }
}
