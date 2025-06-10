package finalmission;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Room {

    @Id
    private final String id;

    public Room() {
        this.id = createID();
    }

    private static String createID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
