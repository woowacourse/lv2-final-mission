package finalmission.reservation.business.dto.request;

import finalmission.medical.model.TreatmentType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationUpdateTreatmentTypeRequestTest {

    @Test
    void id는_null이_될_수_없다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> new ReservationUpdateTreatmentTypeRequest(null, TreatmentType.EXTRACTION, "username"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id는 null이 될 수 없습니다.");
    }

    @Test
    void treatmentType은_null이_될_수_없다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> new ReservationUpdateTreatmentTypeRequest(1L, null, "username"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("진료 종류는 null이 될 수 없습니다.");
    }

    @Test
    void username은_null이_될_수_없다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> new ReservationUpdateTreatmentTypeRequest(1L, TreatmentType.EXTRACTION, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 아이디는 null이 될 수 없습니다.");
    }

}