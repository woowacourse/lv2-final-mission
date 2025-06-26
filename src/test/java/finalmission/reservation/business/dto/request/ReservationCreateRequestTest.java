package finalmission.reservation.business.dto.request;

import finalmission.medical.model.TreatmentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ReservationCreateRequestTest {

    @Test
    void treatmentType은_null이_될_수_없다 () {
        // Given
        LocalDate date = LocalDate.now().plusDays(1);
        Long timeId = 1L;
        String username = "username";

        // When & Then
        Assertions.assertThatThrownBy(() -> new ReservationCreateRequest(null, date, timeId, username))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("진료 종류는 null이 될 수 없습니다.");
    }

    @Test
    void date는_null이_될_수_없다 () {
        // Given
        TreatmentType treatmentType = TreatmentType.EXTRACTION;
        Long timeId = 1L;
        String username = "username";

        // When & Then
        Assertions.assertThatThrownBy(() -> new ReservationCreateRequest(treatmentType, null, timeId, username))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 날짜는 null이 될 수 없습니다.");
    }

    @Test
    void timeId는_null이_될_수_없다 () {
        // Given
        TreatmentType treatmentType = TreatmentType.EXTRACTION;
        LocalDate date = LocalDate.now().plusDays(1);
        String username = "username";

        // When & Then
        Assertions.assertThatThrownBy(() -> new ReservationCreateRequest(treatmentType, date, null, username))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간은 null이 될 수 없습니다.");
    }

    @Test
    void username은_null이_될_수_없다 () {
        // Given
        TreatmentType treatmentType = TreatmentType.EXTRACTION;
        LocalDate date = LocalDate.now().plusDays(1);
        Long timeId = 1L;

        // When & Then
        Assertions.assertThatThrownBy(() -> new ReservationCreateRequest(treatmentType, date, timeId, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 아이디는 null이 될 수 없습니다.");
    }
}
