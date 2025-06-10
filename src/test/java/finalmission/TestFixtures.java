package finalmission;

import finalmission.domain.Address;
import finalmission.domain.Gym;
import finalmission.domain.Member;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TestFixtures {

    private static final AtomicLong SEQUENCE_GENERATOR = new AtomicLong();

    public static Member anyMember() {
        var id = "mem" + SEQUENCE_GENERATOR.incrementAndGet();
        return new Member(id, "test", "name");
    }

    public static Gym anyGym() {
        var name = "gym" + SEQUENCE_GENERATOR.incrementAndGet();
        return new Gym(name, new Address("test street", "test detail"));
    }

    private static UUID generateUUID() {
        return UUID.randomUUID();
    }
}
