package finalmission.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.fixture.MemberFixture;
import finalmission.member.application.dto.MemberRequest;
import finalmission.member.application.dto.MemberResponse;
import finalmission.member.domain.StubPasswordEncoder;
import finalmission.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({
        MemberService.class,
        StubPasswordEncoder.class,
        StubRandomNameGenerator.class
})
class MemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @DisplayName("멤버를 생성한다")
    @Test
    void create() {
        // given
        String email = "test@example.com";
        MemberRequest request = new MemberRequest(email, "password");

        // when
        MemberResponse response = memberService.create(request);

        // then
        assertThat(response.id()).isNotNull();
        assertThat(response.email()).isEqualTo(email);
        assertThat(response.name()).isNotNull();
    }

    @DisplayName("이미 존재하는 이메일로는 멤버를 생성할 수 없다")
    @Test
    void create_fail_with_duplicated_email() {
        // given
        String email = "test@example.com";
        memberRepository.save(MemberFixture.createMember("test", email, "1234"));
        MemberRequest request = new MemberRequest(email, "password");

        // when & then
        assertThatThrownBy(() -> memberService.create(request))
                .isInstanceOf(IllegalArgumentException.class);

    }
}
