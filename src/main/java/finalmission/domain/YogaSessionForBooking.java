package finalmission.domain;

public record YogaSessionForBooking(
        YogaSession session,
        long currentAttendance,
        boolean isFullBooking
) {

    public static YogaSessionForBooking of(YogaSession session, long attendance) {
        var isFullBooking = session.isOverCapacity(attendance + 1);
        return new YogaSessionForBooking(session, attendance, isFullBooking);
    }
}
