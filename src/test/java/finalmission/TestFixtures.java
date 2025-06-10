package finalmission;

import finalmission.domain.Address;
import finalmission.domain.Booking;
import finalmission.domain.BookingDate;
import finalmission.domain.Gym;
import finalmission.domain.Member;
import java.time.LocalDate;
import java.util.Random;
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

    public static BookingDate anyDate() {
        return BookingDate.of(
            LocalDate.of(
                new Random().nextInt(2001, 2025),
                new Random().nextInt(1, 12),
                new Random().nextInt(1, 28)
            )
        );
    }

    public static Booking anyBooking() {
        return new Booking(anyMember(), anyGym(), anyDate());
    }

    private static UUID generateUUID() {
        return UUID.randomUUID();
    }
}
