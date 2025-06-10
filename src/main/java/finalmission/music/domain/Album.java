package finalmission.music.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String artistName;
    private int totalTracks;
    private LocalDate releaseDate;
    private String spotify_id;

    public Album(String name, String artistName, int totalTracks, LocalDate releaseDate,
                 String spotify_id) {
        this.name = name;
        this.artistName = artistName;
        this.totalTracks = totalTracks;
        this.releaseDate = releaseDate;
        this.spotify_id = spotify_id;
    }
}
