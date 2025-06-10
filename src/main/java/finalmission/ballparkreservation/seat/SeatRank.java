package finalmission.ballparkreservation.seat;

public enum SeatRank {
    TABLE(55_000, 88_000),
    CHEERING(20_000, 28_000),
    THIRD_FLOOR(14_000, 21_000);

    private final int weekdayPrice;
    private final int holidayPrice;

    SeatRank(final int weekdayPrice, final int holidayPrice) {
        this.weekdayPrice = weekdayPrice;
        this.holidayPrice = holidayPrice;
    }

    public int getWeekdayPrice() {
        return weekdayPrice;
    }

    public int getHolidayPrice() {
        return holidayPrice;
    }
}
