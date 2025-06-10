package finalmission.ballparkreservation.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    private String email;
    private String password;
    private String name;
    private int age;

    public Member(final String email, final String password, final String name, final int age) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
    }

    public boolean isDiscountApply() {
        return age <= 19 || age >= 60;
    }
}
