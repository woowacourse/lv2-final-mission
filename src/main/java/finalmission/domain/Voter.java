package finalmission.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Voter {

    @EmbeddedId
    private final Id id = Id.random();
    private final String name;
    private final String password;
    @ManyToOne
    private final Room room;

    public Voter(String name, String password, Room room) {
        this.name = name;
        this.password = password;
        this.room = room;
        room.addMember(this);
    }

    public boolean hasName(String name) {
        return this.name.equals(name);
    }

    public void validatePassword(String password) {
        if (!this.password.equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
