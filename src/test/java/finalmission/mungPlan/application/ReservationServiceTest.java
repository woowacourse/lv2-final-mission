package finalmission.mungPlan.application;

import static finalmission.mungPlan.TestFixture.DEFAULT_DATE;
import static finalmission.mungPlan.TestFixture.createSampleUser;
import static org.assertj.core.api.Assertions.assertThat;

import finalmission.mungPlan.domain.PlanDate;
import finalmission.mungPlan.domain.TimeSlot;
import finalmission.mungPlan.domain.User;
import finalmission.mungPlan.infra.PlanDateRepository;
import finalmission.mungPlan.infra.ReservationRepository;
import finalmission.mungPlan.infra.UserRepository;
import finalmission.mungPlan.ui.dto.CreateReservationRequest;
import finalmission.mungPlan.ui.dto.ReservationResponse;
import java.time.LocalTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(ReservationService.class)
class ReservationServiceTest {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    PlanDateRepository planDateRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReservationService reservationService;

    @DisplayName("예약을 저장할 수 있다.")
    @Test
    void saveReservation() {
        // given
        PlanDate planDate = planDateRepository.save(PlanDate.createNew(DEFAULT_DATE));
        TimeSlot timeSlot1 = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(11 ,0));
        TimeSlot timeSlot2 = new TimeSlot(LocalTime.of(11, 0), LocalTime.of(12, 0));
        planDate.addTimeSlot(timeSlot1);
        planDate.addTimeSlot(timeSlot2);

        User user = userRepository.save(createSampleUser());

        // when
        CreateReservationRequest createReservationRequest =
                new CreateReservationRequest(planDate.getId(), LocalTime.of(10, 0), user.getId());
        ReservationResponse response = reservationService.createReservation(createReservationRequest);

        // then
        SoftAssertions.assertSoftly(softly -> {
            assertThat(response.date()).isEqualTo(DEFAULT_DATE);
            assertThat(response.time().startAt()).isEqualTo(timeSlot1.getStartAt());
            assertThat(response.user().id()).isEqualTo(user.getId());
        });
    }

}
