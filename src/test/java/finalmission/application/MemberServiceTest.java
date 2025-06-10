package finalmission.application;

import finalmission.domain.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MemberServiceTest {

    private final MemberRepository memberRepository = Mockito.mock(MemberRepository.class);
    private final MemberService memberService = new MemberService(memberRepository);

    @Test
    @DisplayName("사용자를 등록한다.")
    void register() {
        memberService.register("popo", "password", "포포");
        Mockito.verify(memberRepository).save(Mockito.any());
    }
}
