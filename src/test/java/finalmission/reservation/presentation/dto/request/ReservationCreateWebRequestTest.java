package finalmission.reservation.presentation.dto.request;

import finalmission.medical.model.TreatmentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ReservationCreateWebRequestTest {

    @Test
    void treatmentType은_null이_될_수_없다 () {
        // Given
        LocalDate date = LocalDate.now().plusDays(1);
        Long timeId = 1L;
        String name = "프리";

        // When & Then
        Assertions.assertThatThrownBy(() -> new ReservationCreateWebRequest(null, date, timeId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("진료 종류는 null이 될 수 없습니다.");
    }

    @Test
    void date는_null이_될_수_없다 () {
        // Given
        TreatmentType treatmentType = TreatmentType.EXTRACTION;
        Long timeId = 1L;
        String name = "프리";

        // When & Then
        Assertions.assertThatThrownBy(() -> new ReservationCreateWebRequest(treatmentType, null, timeId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 날짜는 null이 될 수 없습니다.");
    }

    @Test
    void timeId는_null이_될_수_없다 () {
        // Given
        TreatmentType treatmentType = TreatmentType.EXTRACTION;
        LocalDate date = LocalDate.now().plusDays(1);
        String name = "프리";

        // When & Then
        Assertions.assertThatThrownBy(() -> new ReservationCreateWebRequest(treatmentType, date, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간은 null이 될 수 없습니다.");
    }
}
