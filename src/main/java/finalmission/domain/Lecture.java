package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Getter
@NoArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode(of = "id")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sport;
    private LocalDate date;

    public Lecture(Long id, String sport, LocalDate date) {
        this.id = id;
        this.sport = sport;
        this.date = date;
    }

    public Lecture(String sport, LocalDate date) {
        this(null, sport, date);
    }
}
