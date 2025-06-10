package finalmission.popupstore.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PopupStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private int maxEnteredMemberCount;

    private PopupStore(final Long id,
                       final String title,
                       final String content,
                       final LocalDateTime startAt,
                       final LocalDateTime endAt,
                       final int maxEnteredMemberCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.startAt = startAt;
        this.endAt = endAt;
        this.maxEnteredMemberCount = maxEnteredMemberCount;
    }

    public static PopupStore open(
            final String title,
            final String content,
            final LocalDateTime startAt,
            final LocalDateTime endAt,
            final int maxEnteredMemberCount
    ) {
        return new PopupStore(null, title, content, startAt, endAt, maxEnteredMemberCount);
    }
}
