package finalmission.subway.domain;

import finalmission.line.Line;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subway {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Line line;
}
