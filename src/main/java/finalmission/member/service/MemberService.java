package finalmission.member.service;

import finalmission.exception.member.DuplicateEmailException;
import finalmission.member.domain.Member;
import finalmission.member.dto.request.MemberRequest;
import finalmission.member.dto.response.MemberResponse;
import finalmission.member.dto.response.NicknameResponse;
import finalmission.member.infrastructure.MemberRepository;
import finalmission.member.infrastructure.NicknameSuggestClient;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final NicknameSuggestClient nicknameSuggestClient;

    public MemberService(MemberRepository memberRepository, NicknameSuggestClient nicknameSuggestClient) {
        this.memberRepository = memberRepository;
        this.nicknameSuggestClient = nicknameSuggestClient;
    }

    public MemberResponse createNewMember(MemberRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException();
        }
        Member savedMember = memberRepository.save(request.toMember());
        return MemberResponse.from(savedMember);
    }

    public NicknameResponse suggestNickname() {
        return new NicknameResponse(nicknameSuggestClient.getNickname());
    }
}
