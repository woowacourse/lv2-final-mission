package finalmission.subway.domain;

import finalmission.line.domain.Line;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Subway {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Line line;

    public Subway(Line line) {
        this.line = line;
    }
}
