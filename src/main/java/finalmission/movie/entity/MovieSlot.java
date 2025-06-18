package finalmission.movie.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class MovieSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Movie movie;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "start_at")
    private LocalTime startAt;

    @Column(name = "seats")
    private Integer seats;

    public MovieSlot(Movie movie, LocalDate date, LocalTime startAt, Integer seats) {
        this.movie = movie;
        this.date = date;
        this.startAt = startAt;
        this.seats = seats;
    }

    protected MovieSlot() {
    }

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public Integer getSeats() {
        return seats;
    }
}
