package finalmission.popupstore.domain;

import finalmission.shopkeeper.domain.Shopkeeper;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @JoinColumn(name = "shopkeeper_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Shopkeeper shopkeeper;

    private PopupStore(final Long id,
                       final String title,
                       final String content,
                       final LocalDateTime startAt,
                       final LocalDateTime endAt,
                       final int maxEnteredMemberCount,
                       final Shopkeeper shopkeeper) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.startAt = startAt;
        this.endAt = endAt;
        this.maxEnteredMemberCount = maxEnteredMemberCount;
        this.shopkeeper = shopkeeper;
    }

    public static PopupStore open(
            final String title,
            final String content,
            final LocalDateTime startAt,
            final LocalDateTime endAt,
            final int maxEnteredMemberCount,
            final Shopkeeper shopkeeper
    ) {
        return new PopupStore(null, title, content, startAt, endAt, maxEnteredMemberCount, shopkeeper);
    }
}
