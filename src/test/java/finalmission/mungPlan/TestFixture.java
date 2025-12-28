package finalmission.mungPlan;

import finalmission.mungPlan.domain.User;
import java.time.LocalDate;

public class TestFixture {

    public static final LocalDate DEFAULT_DATE = LocalDate.now().plusDays(1);

    public static User createSampleUser() {
        return User.createNew("멍구", "test@email.com");
    }

    public static User createUserByName(String name) {
        return User.createNew(name, name + "@email.com");
    }
}
