package finalmission.reservatioin.entity;

public class ReservationWithNumberOfPeople {

    private final Reservation reservation;
    private final long rank;

    public ReservationWithNumberOfPeople(Reservation reservation, long rank) {
        this.reservation = reservation;
        this.rank = rank;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public long getRank() {
        return rank;
    }
}
