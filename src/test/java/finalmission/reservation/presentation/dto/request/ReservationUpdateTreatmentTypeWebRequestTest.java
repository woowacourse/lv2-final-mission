package finalmission.reservation.presentation.dto.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationUpdateTreatmentTypeWebRequestTest {

    @Test
    void treatmentType은_null이_될_수_없다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> new ReservationUpdateTreatmentTypeWebRequest(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("진료 종류는 null이 될 수 없습니다.");
    }
}
