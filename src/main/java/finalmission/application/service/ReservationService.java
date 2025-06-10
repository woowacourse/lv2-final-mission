package finalmission.application.service;

import finalmission.application.dto.ReservationDateTimeRequest;
import finalmission.application.dto.ReservationRequest;
import finalmission.domain.Coach;
import finalmission.domain.Crew;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationDateTime;
import finalmission.domain.repository.CoachRepository;
import finalmission.domain.repository.CrewRepository;
import finalmission.domain.repository.ReservationDateTimeRepository;
import finalmission.domain.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final CoachRepository coachRepository;
    private final CrewRepository crewRepository;
    private final ReservationDateTimeRepository reservationDateTimeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(final CoachRepository coachRepository, final CrewRepository crewRepository,
                              final ReservationDateTimeRepository reservationDateTimeRepository,
                              final ReservationRepository reservationRepository) {
        this.coachRepository = coachRepository;
        this.crewRepository = crewRepository;
        this.reservationDateTimeRepository = reservationDateTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    public Long createReservation(final ReservationRequest reservationRequest) {
        Coach coach = coachRepository.findByMemberNickname(reservationRequest.getCoach())
                .orElseThrow(() -> new IllegalArgumentException("코치가 존재하지 않습니다"));

        Crew crew = crewRepository.findById(reservationRequest.getCrewId())
                .orElseThrow(() -> new IllegalArgumentException("크루가 존재하지 않습니다"));

        ReservationDateTime reservationDateTime = reservationRepository.findByReservationDateTime(reservationRequest.getLocalDateTime())
                .orElseThrow(() -> new IllegalArgumentException("시간이 존재하지 않습니다"));

        Reservation reservation = new Reservation(
                coach,
                crew,
                reservationDateTime
        );

        return reservationRepository.save(reservation).getId();
    }
}
