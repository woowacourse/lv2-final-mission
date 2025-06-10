package finalmission.mungPlan;

import finalmission.mungPlan.domain.User;
import java.time.LocalDate;

public class TestFixture {

    public static final LocalDate DEFAULT_DATE = LocalDate.now().plusDays(1);

    public static User createSampleUser() {
        return User.createNew("user1", "멍구");
    }
}
