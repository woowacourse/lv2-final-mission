package finalmission.party.domain;

import finalmission.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Party extends BaseEntity {

    private PartyStatus partyStatus;

    public static Party init() {
        return new Party(PartyStatus.OPEN);
    }

    public void open() {
        requireClose();
        this.partyStatus = PartyStatus.OPEN;
    }

    public void close() {
        requireOpen();
        this.partyStatus = PartyStatus.CLOSED;
    }

    public void requireOpen() {
        if (partyStatus.isOpen()) {
            return;
        }
        throw new IllegalStateException("마감인데요?");
    }

    private void requireClose() {
        if (partyStatus.isClosed()) {
            return;
        }
        throw new IllegalStateException("진행 중인데요?");
    }
}
