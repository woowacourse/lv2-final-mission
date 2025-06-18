package finalmission.fixture;

import finalmission.member.domain.Email;
import finalmission.member.domain.Member;
import finalmission.member.domain.Name;
import finalmission.member.domain.Password;
import finalmission.member.domain.StubPasswordEncoder;

public class MemberFixture {
    public static Member createMember(String name, String email, String password) {
        return new Member(new Name(name), new Email(email), new Password(password, new StubPasswordEncoder()));
    }
}
