package finalmission.reservatioin.entity;

import java.time.LocalTime;

public enum ReservationTime {
    LUNCH(LocalTime.of(12, 0)),
    DINNER(LocalTime.of(18, 0));

    private final LocalTime startTime;

    ReservationTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public static ReservationTime toReservationTime(LocalTime localTime) {
        if (localTime.equals(LUNCH.startTime)) {
            return LUNCH;
        }

        if (localTime.equals(DINNER.startTime)) {
            return DINNER;
        }

        throw new IllegalArgumentException("[ERROR] 잘못된 시간을 입력하셨습니다. 점심 또는 저녁 시간만 입력해 주세요.");
    }

}
