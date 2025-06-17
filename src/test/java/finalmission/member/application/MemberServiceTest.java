package finalmission.member.application;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.common.CleanUp;
import finalmission.member.application.in.dto.SignupMember;
import finalmission.member.application.out.client.RandomNicknameGenerator;
import finalmission.member.application.out.dto.SignedMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private CleanUp cleanUp;

    @BeforeEach
    void setUp() {
        cleanUp.all();
    }

    @TestConfiguration
    static class RandomNicknameGeneratorTestConfig {
        @Bean
        public RandomNicknameGenerator randomNicknameGenerator() {
            return () -> "STUB_NICKNAME";
        }
    }

    @Test
    void 멤버가_가입한다() {
        // given
        final SignupMember command = new SignupMember("이름");

        // when
        SignedMember signedMember = memberService.signUp(command);

        // then
        assertThat(signedMember.name()).isEqualTo(command.name());
        assertThat(signedMember.nickname()).isEqualTo("STUB_NICKNAME");
    }
}
