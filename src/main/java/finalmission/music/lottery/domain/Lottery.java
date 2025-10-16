package finalmission.music.lottery.domain;

import finalmission.music.album.domain.Album;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Album album;

    private LocalDateTime expirationDateTime;

    public Lottery(final Album album, final LocalDateTime expirationDateTime) {
        this.album = album;
        this.expirationDateTime = expirationDateTime;
    }

    public boolean isExpired() {
        return expirationDateTime.isBefore(LocalDateTime.now());
    }
}
