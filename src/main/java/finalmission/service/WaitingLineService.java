package finalmission.service;

import finalmission.domain.Member;
import finalmission.domain.Store;
import finalmission.domain.WaitingLine;
import finalmission.repository.WaitingLineRepository;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class WaitingLineService {

    private final WaitingLineRepository waitingLineRepository;

    public WaitingLineService(final WaitingLineRepository waitingLineRepository) {
        this.waitingLineRepository = waitingLineRepository;
    }

    @Transactional
    public void addWaitingMember(Store store, Member member) {
        WaitingLine waitingLine = findByStoreId(store.getId());
        if (waitingLine.hasMember(member)) {
            throw new IllegalStateException("이미 대기 중인 회원입니다.");
        }
        waitingLine.addMember(member);
    }

    @Transactional
    public void removeWaitingMember(Store store, Member member) {
        WaitingLine waitingLine = findByStoreId(store.getId());
        if (!waitingLine.hasMember(member)) {
            throw new IllegalStateException("대기 목록에 없는 회원입니다.");
        }
        waitingLine.removeMember(member);
    }

    public int getWaitingRank(Store store, Member member) {
        WaitingLine waitingLine = findByStoreId(store.getId());
        return waitingLine.getSequenceByMember(member);
    }

    public WaitingLine findByStoreId(Long storeId) {
        return waitingLineRepository.findByStoreId(storeId)
                .orElseThrow(() -> new NoSuchElementException("해당 가게의 대기열이 존재하지 않습니다."));
    }
}
