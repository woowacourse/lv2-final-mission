package finalmission.domain.service;

import finalmission.domain.entity.LessonTime;
import finalmission.domain.entity.Member;
import finalmission.domain.entity.Reservation;
import finalmission.domain.entity.ReservationStatus;
import finalmission.domain.entity.Trainer;
import finalmission.domain.service.dto.ReservationLessonRequest;
import finalmission.domain.service.dto.TrainerReservationResponse;
import finalmission.repository.LessonTimeRepository;
import finalmission.repository.ReservationRepository;
import finalmission.repository.TrainerRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TrainerRepository trainerRepository;
    private final LessonTimeRepository lessonTimeRepository;
    private final TimeInject timeInject;

    public ReservationService(ReservationRepository reservationRepository, TrainerRepository trainerRepository,
                              LessonTimeRepository lessonTimeRepository,
                              TimeInject timeInject) {
        this.reservationRepository = reservationRepository;
        this.trainerRepository = trainerRepository;
        this.lessonTimeRepository = lessonTimeRepository;
        this.timeInject = timeInject;
    }

    public List<TrainerReservationResponse> getTrainersReservations(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 선생님입니다: " + trainerId));
        List<Reservation> foundReservations = reservationRepository.findByTrainerAndDateAfterOrderByDateAsc(
                trainer,
                timeInject.now().toLocalDate()
        );
        return foundReservations.stream().map(reservation -> TrainerReservationResponse.from(
                reservation.getDate(),
                reservation.reservedTime()
        )).toList();
    }

    @Transactional
    public void reserveLesson(Member member, ReservationLessonRequest request) {
        if (!member.matchTrainer(request.trainerId())) {
            throw new IllegalArgumentException("[ERROR] 담당 선생님의 수업만 수강할 수 있습니다");
        }
        if (!member.availableLesson()) {
            throw new IllegalArgumentException("[ERROR] PT 수강권이 있어야 수강할 수 있습니다");
        }
        LessonTime time = lessonTimeRepository.findById(request.lessonTimeId())
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 수강 시간입니다: " + request.lessonTimeId()));
        Trainer trainer = trainerRepository.findById(request.trainerId())
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 선생님입니다: " + request.trainerId()));
        if (reservationRepository.existsByDateAndLessonTimeAndTrainer(request.date(), time, trainer)) {
            createLesson(request, time, trainer, member, ReservationStatus.WAITING);
            return;
        }
        createLesson(request, time, trainer, member, ReservationStatus.RESERVED);
        member.minusPtNumber();
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
}
