package finalmission.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeName;

    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus;

    private String description;

    private Double starRating;

    @OneToOne
    private Member member;

    @OneToOne(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private WaitingLine waitingLine;

    public Store(String storeName, StoreStatus storeStatus, String description, Double starRating, Member member) {
        this(null, storeName, storeStatus, description, starRating, member, null);
        this.waitingLine = WaitingLine.makeNewWaiting(this);
    }

    public void updateStatus(StoreStatus status) {
        this.storeStatus = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Store store = (Store) o;
        return Objects.equals(id, store.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void addWaiting(Member member) {
        this.waitingLine.addMember(member);
    }

    public int getWaitingRank(Member member) {
        return this.waitingLine.getSequenceByMember(member);
    }
}
