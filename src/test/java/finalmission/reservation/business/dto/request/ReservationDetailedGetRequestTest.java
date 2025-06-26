package finalmission.reservation.business.dto.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationDetailedGetRequestTest {

    @Test
    void id는_null이_될_수_없다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> new ReservationDetailedGetRequest(null, "username"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id는 null이 될 수 없습니다.");
    }

    @Test
    void username은_null이_될_수_없다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> new ReservationDetailedGetRequest(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 아이디는 null이 될 수 없습니다.");
    }

}