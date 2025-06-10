package finalmission.ballparkreservation.util;

import finalmission.ballparkreservation.member.Member;
import org.springframework.test.util.ReflectionTestUtils;

public class TestFactory {

    public static Member memberWithId(Long id, Member member) {
        ReflectionTestUtils.setField(member, "id", id);
        return member;
    }
}
