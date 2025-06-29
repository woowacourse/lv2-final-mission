package finalmission.restaurant.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RestaurantTest {

    @Test
    void 이름이_null이면_예외가_발생한다() {
        // given
        final String name = null;
        final String description = "12년 전통의 잠실 대표 중식당입니다";
        final String place = "서울 송파구 신천동 7-22";
        final String phoneNumber = "0507-1397-4624";
        final int maxReservationCount = 20;

        // when & then
        Assertions.assertThatThrownBy(() -> new Restaurant(name, description, place, phoneNumber, maxReservationCount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "      "})
    void 이름이_blank면_예외가_발생한다(final String name) {
        // given
        final String description = "12년 전통의 잠실 대표 중식당입니다";
        final String place = "서울 송파구 신천동 7-22";
        final String phoneNumber = "0507-1397-4624";
        final int maxReservationCount = 20;

        // when & then
        Assertions.assertThatThrownBy(() -> new Restaurant(name, description, place, phoneNumber, maxReservationCount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 설명이_null이면_예외가_발생한다() {
        // given
        final String name = "차이나 스토리";
        final String description = null;
        final String place = "서울 송파구 신천동 7-22";
        final String phoneNumber = "0507-1397-4624";
        final int maxReservationCount = 20;

        // when & then
        Assertions.assertThatThrownBy(() -> new Restaurant(name, description, place, phoneNumber, maxReservationCount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 전화번호가_null이면_예외가_발생한다() {
        // given
        final String name = "차이나 스토리";
        final String description = "12년 전통의 잠실 대표 중식당입니다";
        final String place = "서울 송파구 신천동 7-22";
        final String phoneNumber = null;
        final int maxReservationCount = 20;

        // when & then
        Assertions.assertThatThrownBy(() -> new Restaurant(name, description, place, phoneNumber, maxReservationCount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "      "})
    void 전화번호가_blank면_예외가_발생한다(final String phoneNumber) {
        // given
        final String name = "차이나 스토리";
        final String description = "12년 전통의 잠실 대표 중식당입니다";
        final String place = "서울 송파구 신천동 7-22";
        final int maxReservationCount = 20;

        // when & then
        Assertions.assertThatThrownBy(() -> new Restaurant(name, description, place, phoneNumber, maxReservationCount))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
