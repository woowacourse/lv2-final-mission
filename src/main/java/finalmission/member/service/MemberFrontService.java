package finalmission.member.service;

import finalmission.member.controller.dto.SignupRequest;
import finalmission.member.controller.dto.MemberResponse;
import finalmission.member.domain.Member;
import finalmission.member.service.detail.MemberCommandService;
import finalmission.member.service.detail.MemberQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberFrontService {

    private final MemberQueryService memberQueryService;

    public MemberResponse get(Long id) {
        final Member member = memberQueryService.get(id);

        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail()
        );
    }

    public List<MemberResponse> getAll() {
        return memberQueryService.getAll().stream()
                .map(member -> new MemberResponse(
                        member.getId(),
                        member.getName(),
                        member.getEmail()
                ))
                .toList();
    }
}
