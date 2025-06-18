package finalmission.service;

import finalmission.dto.request.CreateBlackListRequest;
import finalmission.dto.response.BlackListResponse;
import finalmission.dto.response.CreateBlackListResponse;
import finalmission.entity.BlackList;
import finalmission.entity.Member;
import finalmission.exception.custom.DuplicatedValueException;
import finalmission.exception.custom.NotExistedValueException;
import finalmission.repository.BlackListRepository;
import finalmission.repository.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BlackListService {

    private final BlackListRepository blackListRepository;
    private final MemberRepository memberRepository;

    public BlackListService(final BlackListRepository blackListRepository,
                            final MemberRepository memberRepository) {
        this.blackListRepository = blackListRepository;
        this.memberRepository = memberRepository;
    }

    public List<BlackListResponse> findAllBlackList() {
        return blackListRepository.findAllFetch()
                .stream()
                .map(BlackListResponse::from)
                .toList();
    }

    public CreateBlackListResponse addBlackList(final CreateBlackListRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new NotExistedValueException("존재하지 않는 유저입니다."));

        if (blackListRepository.existsByMemberId(member.getId())) {
            throw new DuplicatedValueException("이미 밴 처리된 유저입니다.");
        }

        BlackList blackList = new BlackList(member, request.reason());
        BlackList saved = blackListRepository.save(blackList);
        return CreateBlackListResponse.from(saved);
    }


    public void deleteBlackList(final Long id) {
        if (!blackListRepository.existsById(id)) {
            throw new NotExistedValueException("존재하지 않는 밴 정보입니다.");
        }
        blackListRepository.deleteById(id);
    }
}
