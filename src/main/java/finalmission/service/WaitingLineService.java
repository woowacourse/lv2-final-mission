package finalmission.service;

import finalmission.domain.Member;
import finalmission.domain.WaitingLine;
import finalmission.dto.response.RankResponse;
import finalmission.dto.response.WaitingLineCreateResponse;
import finalmission.infra.auth.LoginMember;
import finalmission.repository.WaitingLineRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WaitingLineService {

    private final WaitingLineRepository waitingLineRepository;

    public WaitingLineService(final WaitingLineRepository waitingLineRepository) {
        this.waitingLineRepository = waitingLineRepository;
    }

    @Transactional
    public WaitingLineCreateResponse save(WaitingLine waitingLine) {
        return WaitingLineCreateResponse.from(waitingLineRepository.save(waitingLine));
    }

    @Transactional(readOnly = true)
    public RankResponse findRankById(Long id, LoginMember loginMember) {
        Optional<WaitingLine> waitingLine = waitingLineRepository.findById(id);
        if (waitingLine.isEmpty()) {
            throw new NoSuchElementException("[ERROR] 대기가 존재하지 않습니다.");
        }
        Member member = new Member(loginMember.id(), loginMember.email(), loginMember.name(), loginMember.memberRole());
        int rank = waitingLine.get().getSequenceByMember(member);

        if (rank == -1) {
            throw new NoSuchElementException("[ERROR] 회원의 대기가 존재하지 않습니다.");
        }
        return new RankResponse(rank);
    }
}
