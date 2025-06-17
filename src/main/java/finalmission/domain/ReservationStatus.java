package finalmission.domain;

public enum ReservationStatus {
    RESERVE("예약"),
    CONFIRM("확정");

    private String displayName;

    ReservationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
