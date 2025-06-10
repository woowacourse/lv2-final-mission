package finalmission.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class MovieReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_name")
    private String memberName;

    @ManyToOne
    private MovieSlot movieSlot;

    @Column(name = "seat")
    private Integer seat;

    public MovieReservation(String memberName, MovieSlot movieSlot, Integer seat) {
        this.memberName = memberName;
        this.movieSlot = movieSlot;
        this.seat = seat;
    }

    protected MovieReservation() {
    }

    public Long getId() {
        return id;
    }

    public String getMemberName() {
        return memberName;
    }

    public MovieSlot getMovieSlot() {
        return movieSlot;
    }

    public Integer getSeat() {
        return seat;
    }
}
