package finalmission.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum Room {

    임팩트("임팩트"),
    백스윙("백스윙"),
    피니시("피니시"),
    톱오브스윙("톱오브스윙"),
    어드레스("어드레스");

    private final String title;

    Room(final String title) {
        this.title = title;
    }

    private static final Map<String, Room> ROOM_CACHING = new HashMap<>();

    static {
        for (final Room room : values()) {
            ROOM_CACHING.put(room.title, room);
        }
    }

    public static Optional<Room> findByTitle(final String title) {
        return Optional.ofNullable(ROOM_CACHING.get(title));
    }

}
