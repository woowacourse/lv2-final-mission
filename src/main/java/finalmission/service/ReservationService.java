package finalmission.service;

import finalmission.domain.member.Coach;
import finalmission.domain.member.Member;
import finalmission.domain.reservation.Reservation;
import finalmission.domain.member.Crew;
import finalmission.domain.reservation.ReservationStatus;
import finalmission.domain.reservation.ReservationTime;
import finalmission.dto.AcceptResultDto;
import finalmission.dto.ReservationRequestDto;
import finalmission.dto.ReservationResponse;
import finalmission.dto.MailRequestDto;
import finalmission.repository.CoachRepository;
import finalmission.repository.ReservationRepository;
import finalmission.repository.CrewRepository;
import finalmission.repository.ReservationTimeRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final MailService mailService;
    private final CoachRepository coachRepository;
    private final CrewRepository crewRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(
        MailService mailService,
        CoachRepository coachRepository,
        CrewRepository crewRepository,
        ReservationRepository reservationRepository,
        ReservationTimeRepository reservationTimeRepository
    ) {
        this.mailService = mailService;
        this.coachRepository = coachRepository;
        this.crewRepository = crewRepository;
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Transactional
    public Reservation save(ReservationRequestDto request, Member member) {
        Coach coach = findCoachById(request.coachId());
        Crew crew = findCrewById(member.getId());
        ReservationTime reservationTime = findTimeById(request.reservationTimeId());
        Reservation reservation = new Reservation(coach, crew, reservationTime, request.date(), ReservationStatus.PENDING);
        reservationRepository.save(reservation);
        sendMailToCoach(reservation);
        return reservation;
    }

    @Transactional
    public void accept(Long reservationId, AcceptResultDto acceptResultDto, Member member) {
        Reservation reservation = findReservationById(reservationId);
        if (!reservation.isOwnerCoachRequest(member.getId())) {
            throw new IllegalStateException("본인의 예약만 수락할 수 있습니다.");
        }
        if (ReservationStatus.isAccepted(acceptResultDto.result())) {
            reservation.changeStatus(ReservationStatus.ACCEPT);
            mailService.sendSimpleMailMessage(MailRequestDto.toCrew(reservation));
            return;
        }
        reservation.changeStatus(ReservationStatus.REJECT);
        mailService.sendSimpleMailMessage(MailRequestDto.toCrew(reservation));
    }

    private void sendMailToCoach(Reservation reservation) {
        MailRequestDto mailRequestDto = MailRequestDto.toCoach(reservation);
        mailService.sendSimpleMailMessage(mailRequestDto);
    }

    @Transactional
    public void deleteFromCrew(Long reservationId, Member member) {
        Reservation reservation = findReservationById(reservationId);
        if (!reservation.isOwnerCrewRequest(member.getId())) {
            throw new IllegalStateException("본인의 예약만 삭제할 수 있습니다.");
        }
        reservationRepository.deleteById(reservationId);
    }

    @Transactional
    public void deleteFromCoach(Long reservationId, Member member) {
        Reservation reservation = findReservationById(reservationId);
        if (!reservation.isOwnerCoachRequest(member.getId())) {
            throw new IllegalStateException("본인의 예약만 삭제할 수 있습니다.");
        }
        reservationRepository.deleteById(reservationId);
    }

    // TODO : 메서드 재활용 해보기
    public List<ReservationResponse> getAllReservationsFromCrewId(Long crewId, Member member) {
        if (!member.getId().equals(crewId)) {
            throw new IllegalStateException("본인의 예약에만 접근할 수 있습니다.");
        }
        List<Reservation> reservations = reservationRepository.findAllByCrewId(crewId);
        return reservations.stream()
            .map(ReservationResponse::from)
            .toList();
    }

    public List<ReservationResponse> getAllReservationsFromCoachId(Long coachId, Member member) {
        if (!member.getId().equals(coachId)) {
            throw new IllegalStateException("본인의 예약에만 접근할 수 있습니다.");
        }

        List<Reservation> reservations = reservationRepository.findAllByCoachId(coachId);
        return reservations.stream()
            .map(ReservationResponse::from)
            .toList();
    }

    private Reservation findReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
    }

    private Coach findCoachById(Long id) {
        return coachRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코치입니다."));
    }

    private Crew findCrewById(Long id) {
        return crewRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

    private ReservationTime findTimeById(Long id) {
        return reservationTimeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다."));
    }

}
