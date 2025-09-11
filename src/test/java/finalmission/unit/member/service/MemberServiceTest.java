package finalmission.unit.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import finalmission.exception.member.DuplicateEmailException;
import finalmission.member.domain.Member;
import finalmission.member.dto.request.MemberRequest;
import finalmission.member.dto.response.MemberResponse;
import finalmission.member.dto.response.NicknameResponse;
import finalmission.member.infrastructure.MemberRepository;
import finalmission.member.infrastructure.NicknameSuggestClient;
import finalmission.member.service.MemberService;
import finalmission.unit.fake.FakeMemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    private final MemberService memberService;
    private final MemberRepository memberRepository = new FakeMemberRepository();
    private final NicknameSuggestClient nicknameSuggestClient = mock(NicknameSuggestClient.class);

    public MemberServiceTest() {
        this.memberService = new MemberService(memberRepository, nicknameSuggestClient);
    }

    @Test
    void 새로운_회원을_생성한다() {
        // given
        MemberRequest request = new MemberRequest("email1@domain.com", "nickname1", "1234");
        // when
        MemberResponse response = memberService.createNewMember(request);
        // then
        assertThat(response.email()).isEqualTo(request.email());
        assertThat(response.nickname()).isEqualTo(request.nickname());
    }

    @Test
    void 중복된_이메일로_회원가입하면_예외가_발생한다() {
        // given
        memberRepository.save(new Member("email1@domain.com", "nickname1", "1234"));
        MemberRequest request = new MemberRequest("email1@domain.com", "nickname2", "1234");
        // when & then
        assertThatThrownBy(() -> memberService.createNewMember(request))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void 닉네임을_추천한다() {
        // given
        String nickname = "nickname1";
        when(nicknameSuggestClient.getNickname()).thenReturn(nickname);
        // when
        NicknameResponse response = memberService.suggestNickname();
        // then
        assertThat(response.nickname()).isEqualTo(nickname);
    }
}