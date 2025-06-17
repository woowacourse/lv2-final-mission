package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime returnTime;

    private Long fee;

    @Builder
    public Rent(Member member, Car car, LocalDate date, LocalTime startTime, LocalTime returnTime) {
        validate(member, car, date, startTime, returnTime);
        this.member = member;
        this.car = car;
        this.date = date;
        this.startTime = startTime;
        this.returnTime = returnTime;
        this.fee = calculateFee(car.getFeePerMinute(), startTime, returnTime);
    }

    private void validate(Member member, Car car, LocalDate date, LocalTime startTime, LocalTime returnTime) {
        if (member == null || car == null || date == null || startTime == null || returnTime == null) {
            throw new IllegalArgumentException("모든 필드는 필수입니다.");
        }
        if (startTime.isAfter(returnTime)) {
            throw new IllegalArgumentException("반납 시간은 시작 시간 이후여야 합니다.");
        }
    }

    private Long calculateFee(Long feePerMinute, LocalTime startTime, LocalTime returnTime) {
        long minutes = Duration.between(startTime, returnTime).toMinutes();
        return feePerMinute * minutes;
    }

    public boolean canBeCanceledBy(Member member) {
        return this.member.equals(member);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Rent rent)) {
            return false;
        }
        return Objects.equals(id, rent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
