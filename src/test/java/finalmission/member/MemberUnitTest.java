package finalmission.member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import finalmission.member.entity.Member;
import finalmission.member.entity.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberUnitTest {

    @Test
    @DisplayName("어드민으로 승격한다.")
    void promoteToAdmin() {
        // given
        Member member = new Member("미소", "미소", "miso@email.com", "miso", RoleType.USER);

        // when
        member.promoteToAdmin();

        // then
        assertThat(member.getRole()).isSameAs(RoleType.ADMIN);
    }

    @Test
    @DisplayName("비밀번호가 일치하면 true를 반환한다.")
    void matchesPassword_true() {
        // given
        Member member = new Member("미소", "미소", "miso@email.com", "miso", RoleType.USER);

        // when
        boolean result = member.matchesPassword("miso");

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("비밀번호가 일치하면 false를 반환한다.")
    void matchesPassword_false() {
        // given
        Member member = new Member("미소", "미소", "miso@email.com", "miso", RoleType.USER);

        // when
        boolean result = member.matchesPassword("hello");

        // then
        assertThat(result).isFalse();
    }
}
