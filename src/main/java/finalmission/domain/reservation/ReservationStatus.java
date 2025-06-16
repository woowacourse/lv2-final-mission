package finalmission.domain.reservation;

public enum ReservationStatus {

    PENDING("대기"),
    ACCEPT("승낙"),
    REJECT("거절");

    private final String message;

    ReservationStatus(String message) {
        this.message = message;
    }

    public static boolean isAccepted(String result) {
        return result.toUpperCase().equals(ACCEPT.name());
    }

    public String getMessage() {
        return message;
    }
}
