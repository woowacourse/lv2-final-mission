package finalmission.application.service;

import finalmission.application.dto.ReservationRequest;
import finalmission.application.dto.ReservationResponse;
import finalmission.domain.Coach;
import finalmission.domain.Crew;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationDateTime;
import finalmission.domain.repository.CoachRepository;
import finalmission.domain.repository.CrewRepository;
import finalmission.domain.repository.ReservationDateTimeRepository;
import finalmission.domain.repository.ReservationRepository;
import finalmission.infrastructure.RandomNameClient;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.RequestContextFilter;

@Service
public class ReservationService {
    private final CoachRepository coachRepository;
    private final CrewRepository crewRepository;
    private final ReservationDateTimeRepository reservationDateTimeRepository;
    private final ReservationRepository reservationRepository;
    private final RandomNameClient randomNameClient;

    public ReservationService(final CoachRepository coachRepository, final CrewRepository crewRepository,
                              final ReservationDateTimeRepository reservationDateTimeRepository,
                              final ReservationRepository reservationRepository, final RandomNameClient randomNameClient) {
        this.coachRepository = coachRepository;
        this.crewRepository = crewRepository;
        this.reservationDateTimeRepository = reservationDateTimeRepository;
        this.reservationRepository = reservationRepository;
        this.randomNameClient = randomNameClient;
    }

    public Long createReservation(final ReservationRequest reservationRequest) {
        Coach coach = coachRepository.findByMemberNickname(reservationRequest.getCoach())
                .orElseThrow(() -> new IllegalArgumentException("코치가 존재하지 않습니다"));

        Crew crew = setCrew(reservationRequest);

        Optional<ReservationDateTime> reservationDateTime = reservationDateTimeRepository.findByCoach(coach)
                .stream().filter(reservationDateTime1 -> reservationDateTime1.getDateTime().equals(reservationRequest.getLocalDateTime()))
                .findFirst();

        if(reservationDateTime.isEmpty()){
            throw new IllegalArgumentException("코치가 가능한 시간이 존재하지 않습니다");
        }

        Reservation reservation = new Reservation(
                coach,
                crew,
                reservationDateTime.get()
        );

        return reservationRepository.save(reservation).getId();
    }

    private Crew setCrew(ReservationRequest reservationRequest) {
        if(reservationRequest.getCrewId()==null){
            return new Crew(randomNameClient.getRandomName());
        }

        return crewRepository.findById(reservationRequest.getCrewId())
                .orElseThrow(() -> new IllegalArgumentException("크루가 존재하지 않습니다"));
    }

    public List<ReservationResponse> getReservations(String crew) {
        return reservationRepository.findAllByCrew_Member_Nickname(crew)
                .stream()
                .map(ReservationResponse::new)
                .toList();
    }

    public ReservationResponse updateReservation(Long id, ReservationRequest reservationRequest) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약이 존재하지 않습니다"));

        final Crew crew = crewRepository.findById(reservationRequest.getCrewId())
                .orElseThrow(() -> new IllegalArgumentException("크루가 존재하지 않습니다"));

        final Coach coach = coachRepository.findByMemberNickname(reservationRequest.getCoach())
                .orElseThrow(() -> new IllegalArgumentException("코치가 존재하지 않습니다"));

        final ReservationDateTime reservationDateTime = reservationDateTimeRepository.findByDateTime(reservationRequest.getLocalDateTime())
                .orElseThrow(() -> new IllegalArgumentException("시간이 존재하지 않습니다"));

        reservation.update(crew, coach, reservationDateTime);

        reservationRepository.saveAndFlush(reservation);

        return new ReservationResponse(reservation);
    }

    public void deleteReservation(Long id) {
        final Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("이미 삭제되어 있는 리소스입니다."));

        reservationRepository.delete(reservation);
    }
}
