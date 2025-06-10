package finalmission.apply.domain;

import finalmission.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Apply extends BaseEntity {

    private Long partyId;
    private Long playerId;

    public static Apply of(final Long partyId, final Long playerId) {
        return new Apply(partyId, playerId);
    }
}
