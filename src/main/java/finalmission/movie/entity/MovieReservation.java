package finalmission.movie.entity;

import finalmission.member.entity.Member;
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

    @ManyToOne
    private Member member;

    @ManyToOne
    private MovieSlot movieSlot;

    @Column(name = "seat")
    private Integer seat;

    public MovieReservation(Member member, MovieSlot movieSlot, Integer seat) {
        this.member = member;
        this.movieSlot = movieSlot;
        this.seat = seat;
    }

    protected MovieReservation() {
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public MovieSlot getMovieSlot() {
        return movieSlot;
    }

    public Integer getSeat() {
        return seat;
    }
}
