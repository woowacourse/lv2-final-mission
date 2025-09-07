package lavatoryreservation.member.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import lavatoryreservation.exception.MemberException;
import lavatoryreservation.member.domain.Email;
import lavatoryreservation.member.domain.Member;
import lavatoryreservation.member.dto.LoginDto;
import lavatoryreservation.member.dto.SignupDto;
import lavatoryreservation.member.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long addMember(SignupDto signupDto) {
        Member member = new Member(null, signupDto.name(), signupDto.email(), signupDto.sex());
        validateDuplicateMember(member);
        return memberRepository.save(member).getId();
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> existMember = memberRepository.findByEmail(new Email(member.getEmail()));
        if (existMember.isPresent()) {
            throw new MemberException("중복된 이메일을 가진 유저가 존재합니다");
        }
    }

    public Member getById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException("존재하지 않는 멤버입니다"));
    }

    public Member getByEmail(LoginDto loginDto) {
        return memberRepository.findByEmail(new Email(loginDto.email()))
                .orElseThrow(() -> new MemberException("존재하지 않는 멤버입니다"));
    }
}
