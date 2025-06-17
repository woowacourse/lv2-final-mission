package finalmission.user.fixture;

import finalmission.user.User;

public class UserFixture {

    public static User create(String name, String role, String email, String password) {
        return new User(name, role, email, password);
    }

    public static User createDefault() {
        return create("user1", "member", "user1@email.com", "user1password");
    }
}
