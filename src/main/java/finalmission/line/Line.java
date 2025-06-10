package finalmission.line;

import java.util.List;
import finalmission.station.domain.Station;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Line {

    @Id
    private Long number;

    @OneToMany
    private List<Station> stations;
}
