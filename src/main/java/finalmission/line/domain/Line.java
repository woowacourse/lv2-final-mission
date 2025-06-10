package finalmission.line.domain;

import java.util.List;
import finalmission.station.domain.Station;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class Line {

    @Id
    private Long number;

    @OneToMany
    private List<Station> stations;
}
