package finalmission.service;

import finalmission.domain.Member;
import finalmission.domain.MemberRole;
import finalmission.domain.Store;
import finalmission.domain.StoreStatus;
import finalmission.domain.WaitingLine;
import finalmission.dto.request.StoreCreateRequest;
import finalmission.dto.response.MemberResponse;
import finalmission.dto.response.StoreCreateResponse;
import finalmission.dto.response.StoreResponse;
import finalmission.dto.response.WaitingLineCreateResponse;
import finalmission.infra.auth.LoginMember;
import finalmission.repository.StoreRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreService {

    private final MemberService memberService;
    private final WaitingLineService waitingLineService;
    private final StoreRepository storeRepository;

    public StoreService(MemberService memberService, WaitingLineService waitingLineService,
                        StoreRepository storeRepository) {
        this.memberService = memberService;
        this.waitingLineService = waitingLineService;
        this.storeRepository = storeRepository;
    }

    @Transactional
    public StoreCreateResponse save(StoreCreateRequest storeCreateRequest, LoginMember loginMember) {
        MemberResponse memberResponse = memberService.findById(loginMember.id());
        Member master = new Member(memberResponse.id(), memberResponse.email(), memberResponse.name(),
                MemberRole.MASTER);
        WaitingLine waitingLine = WaitingLine.makeNewWaiting();
        WaitingLineCreateResponse waitingLineCreateResponse = waitingLineService.save(waitingLine);
        WaitingLine savedWaitingLine = new WaitingLine(waitingLineCreateResponse.id(),
                waitingLineCreateResponse.waiting());
        Store store = new Store(storeCreateRequest.storeName(), StoreStatus.CLOSED, storeCreateRequest.description(),
                0.0, master, savedWaitingLine);

        return StoreCreateResponse.from(storeRepository.save(store));
    }

    @Transactional(readOnly = true)
    public StoreResponse findById(Long id) {
        Optional<Store> store = storeRepository.findById(id);

        if (store.isEmpty()) {
            throw new NoSuchElementException("[ERROR] 가게가 존재하지 않습니다.");
        }

        return StoreResponse.from(store.get());
    }
}
