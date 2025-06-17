package finalmission.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void 멤버를_생성한다() {
        // given
        final String name = "이름";
        final String nickname = "닉네임";

        // when
        final Member member = Member.create(name, nickname);

        // then
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getNickname()).isEqualTo(nickname);
    }

}