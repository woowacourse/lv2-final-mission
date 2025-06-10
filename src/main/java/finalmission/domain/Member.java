package finalmission.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Member {

    @EmbeddedId
    private final Id id = Id.random();
    private final String name;
    private final String password;

    public boolean hasName(String name) {
        return this.name.equals(name);
    }
}
