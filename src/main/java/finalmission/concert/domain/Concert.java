package finalmission.concert.domain;

import finalmission.common.exception.InvalidInputException;
import finalmission.venue.domain.Venue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 50)
    private String artist;

    private LocalDateTime concertDate;

    @OneToOne
    @JoinColumn(nullable = false)
    private Venue venue;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false, length = 200)
    private String description;

    public Concert(final String title, final String artist, final LocalDateTime concertDate, final Venue venue,
                   final Long price, final String description) {
        validate(title, artist, concertDate, venue, price, description);
        this.title = title;
        this.artist = artist;
        this.concertDate = concertDate;
        this.venue = venue;
        this.price = price;
        this.description = description;
    }

    private void validate(final String title, final String artist, final LocalDateTime concertDate, final Venue venue,
                          final Long price, final String description) {
        if (title == null || title.isBlank()) {
            throw new InvalidInputException("제목은 null이거나 빈 값일 수 없습니다.");
        }
        if (artist == null || artist.isBlank()) {
            throw new InvalidInputException("아티스트는 null이거나 빈 값일 수 없습니다.");
        }
        if (concertDate == null) {
            throw new InvalidInputException("공연 날짜는 null일 수 없습니다.");
        }
        if (venue == null) {
            throw new InvalidInputException("공연장은 null일 수 없습니다.");
        }
        if (price <= 0) {
            throw new InvalidInputException("가격은 0보다 커야 합니다.");
        }
        if (description == null || description.isBlank()) {
            throw new InvalidInputException("설명은 null이거나 빈 값일 수 없습니다.");
        }
    }
}
