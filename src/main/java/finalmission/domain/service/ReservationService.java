package finalmission.domain.service;

import finalmission.domain.entity.LessonTime;
import finalmission.domain.entity.Member;
import finalmission.domain.entity.Reservation;
import finalmission.domain.entity.ReservationStatus;
import finalmission.domain.entity.Trainer;
import finalmission.domain.service.dto.ReservationDetailResponse;
import finalmission.domain.service.dto.ReservationLessonRequest;
import finalmission.domain.service.dto.TrainerReservationResponse;
import finalmission.domain.repository.LessonTimeRepository;
import finalmission.domain.repository.ReservationRepository;
import finalmission.domain.repository.TrainerRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TrainerRepository trainerRepository;
    private final LessonTimeRepository lessonTimeRepository;
    private final TimeInjection timeInjection;

    public ReservationService(ReservationRepository reservationRepository, TrainerRepository trainerRepository,
                              LessonTimeRepository lessonTimeRepository,
                              TimeInjection timeInjection) {
        this.reservationRepository = reservationRepository;
        this.trainerRepository = trainerRepository;
        this.lessonTimeRepository = lessonTimeRepository;
        this.timeInjection = timeInjection;
    }

    @Transactional
    public void reserveLesson(Member member, ReservationLessonRequest request) {
        if (!member.matchTrainer(request.trainerId())) {
            throw new IllegalArgumentException("[ERROR] 담당 선생님의 수업만 수강할 수 있습니다");
        }
        if (!member.availableLesson()) {
            throw new IllegalArgumentException("[ERROR] PT 수강권이 있어야 수강할 수 있습니다");
        }
        LessonTime time = getLessonTime(request.lessonTimeId());
        Trainer trainer = getTrainer(request.trainerId());
        if (reservationRepository.existsByDateAndLessonTimeAndTrainer(request.date(), time, trainer)) {
            createLesson(request, time, trainer, member, ReservationStatus.WAITING);
            return;
        }
        createLesson(request, time, trainer, member, ReservationStatus.RESERVED);
        member.minusPtNumber();
    }

    public List<TrainerReservationResponse> getTrainersReservations(Long trainerId) {
        Trainer trainer = getTrainer(trainerId);
        List<Reservation> foundReservations = reservationRepository.findByTrainerAndDateAfterOrderByDateAsc(
                trainer,
                timeInjection.now().toLocalDate()
        );
        return foundReservations.stream().map(reservation -> TrainerReservationResponse.from(
                reservation.getDate(),
                reservation.reservedTime()
        )).toList();
    }

    public List<ReservationDetailResponse> getReservationsMemberState(
            Member member,
            ReservationStatus reservationStatus
    ) {
        List<Reservation> reservations = reservationRepository.findByMemberAndStatus(member, reservationStatus);
        return reservations.stream().map(reservation ->
                        ReservationDetailResponse.from(
                                reservation.getTrainer().getName(),
                                reservation.getDate(),
                                reservation.reservedTime()
                        )
                )
                .toList();
    }

    @Transactional
    public void cancelReservation(Member member, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 예약입니다: " + reservationId));
        if (!member.equalMember(reservation.getMember())) {
            throw new IllegalArgumentException("[ERROR] 권한이 없습니다");
        }
        if (reservation.isReserved() && reservation.availRefund(timeInjection.now().toLocalDate())) {
            member.refundPtNumber();
        }
        reservationRepository.delete(reservation);
    }

    private void createLesson(
            ReservationLessonRequest request,
            LessonTime time,
            Trainer trainer,
            Member member,
            ReservationStatus status
    ) {
        Reservation reservation = Reservation.createWithoutId(status, request.date(), member, time, trainer);
        reservationRepository.save(reservation);
    }

    private Trainer getTrainer(Long request) {
        return trainerRepository.findById(request)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 선생님입니다: " + request));
    }

    private LessonTime getLessonTime(Long lessonTimeId) {
        return lessonTimeRepository.findById(lessonTimeId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 수강 시간입니다: " + lessonTimeId));
    }
}
