package finalmission.application.dto;

import finalmission.domain.Reservation;
import java.time.LocalDateTime;

public class ReservationResponse {
    private Long id;
    private String coach;
    private LocalDateTime dateTime;

    private ReservationResponse(){
    }

    public ReservationResponse(Reservation reservation){
        this.id = reservation.getId();
        this.coach = reservation.getCoach().getMember().getNickname();
        this.dateTime = reservation.getReservationDateTime().getDateTime();
    }

    public Long getId() {
        return id;
    }

    public String getCoach() {
        return coach;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
