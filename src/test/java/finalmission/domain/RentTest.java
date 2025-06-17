package finalmission.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class RentTest {

    private final Member member = Member.builder().name("홍길동").build();
    private final Car car = Car.builder().name("소나타").licensePlate("123하4567").feePerMinute(100L).build();
    private final LocalDate date = LocalDate.of(2025, 6, 18);
    private final LocalTime start = LocalTime.of(10, 0);
    private final LocalTime end = LocalTime.of(12, 0);

    @Test
    @DisplayName("정상 생성 시 요금이 올바르게 계산된다")
    void createRentSuccessfully() {
        // given
        Rent rent = Rent.builder()
                .member(member)
                .car(car)
                .date(date)
                .startTime(start)
                .returnTime(end)
                .build();

        // when
        // then
        assertAll(() -> {
            assertThat(rent.getFee()).isEqualTo(100L * 120); // 2시간 = 120분
            assertThat(rent.getCar()).isEqualTo(car);
            assertThat(rent.getMember()).isEqualTo(member);
        });
    }

    @Test
    @DisplayName("시작 시간이 반납 시간보다 이후일 경우 예외 발생")
    void startAfterEndThrows() {
        // given
        LocalTime startTime = LocalTime.of(15, 0);
        LocalTime returnTime = LocalTime.of(14, 0);

        // when
        // then
        assertThatCode(() -> Rent.builder()
                .member(member)
                .car(car)
                .date(date)
                .startTime(startTime)
                .returnTime(returnTime)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("반납 시간은 시작 시간 이후여야 합니다.");
    }

    @Test
    @DisplayName("필수 필드가 null이면 예외 발생")
    void nullFieldsThrow() {
        // given
        // when
        // then
        assertThatCode(() -> Rent.builder()
                .member(null)
                .car(car)
                .date(date)
                .startTime(start)
                .returnTime(end)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("모든 필드는 필수입니다.");
        assertThatCode(() -> Rent.builder()
                .member(member)
                .car(null)
                .date(date)
                .startTime(start)
                .returnTime(end)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("모든 필드는 필수입니다.");
        assertThatCode(() -> Rent.builder()
                .member(member)
                .car(car)
                .date(null)
                .startTime(start)
                .returnTime(end)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("모든 필드는 필수입니다.");
    }

    @Test
    @DisplayName("자신의 예약이면 취소 가능")
    void canBeCanceledByOwner() {
        // given
        Rent rent = Rent.builder()
                .member(member)
                .car(car)
                .date(date)
                .startTime(start)
                .returnTime(end)
                .build();

        // when
        // then
        assertThat(rent.canBeCanceledBy(member))
                .isTrue();
    }

    @Test
    @DisplayName("다른 사용자의 예약은 취소할 수 없다")
    void cannotBeCanceledByOther() {
        // given
        Member other = Member.builder().name("임꺽정").build();
        ReflectionTestUtils.setField(other, "id", 2L); // 다른 ID 설정
        Rent rent = Rent.builder()
                .member(member)
                .car(car)
                .date(date)
                .startTime(start)
                .returnTime(end)
                .build();

        // when
        // then
        assertThat(rent.canBeCanceledBy(other))
                .isFalse();
    }

    @Test
    @DisplayName("동일한 ID를 가지면 equals는 true를 반환한다")
    void testEqualsById() {
        // given
        Rent rent1 = Rent.builder()
                .member(member)
                .car(car)
                .date(date)
                .startTime(start)
                .returnTime(end)
                .build();
        Rent rent2 = Rent.builder()
                .member(member)
                .car(car)
                .date(date)
                .startTime(start)
                .returnTime(end)
                .build();
        ReflectionTestUtils.setField(rent1, "id", 1L);
        ReflectionTestUtils.setField(rent2, "id", 1L);

        // when
        // then
        assertAll(() -> {
            assertThat(rent1).isEqualTo(rent2);
            assertThat(rent1.hashCode()).isEqualTo(rent2.hashCode());
        });
    }
}
