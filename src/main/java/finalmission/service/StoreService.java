package finalmission.service;

import finalmission.domain.Member;
import finalmission.domain.MemberRole;
import finalmission.domain.Store;
import finalmission.domain.StoreStatus;
import finalmission.dto.request.AddWaitingRequest;
import finalmission.dto.request.StoreCreateRequest;
import finalmission.dto.response.AddWaitingResponse;
import finalmission.dto.response.MemberResponse;
import finalmission.dto.response.RankResponse;
import finalmission.dto.response.StoreCreateResponse;
import finalmission.dto.response.StoreResponse;
import finalmission.infra.auth.LoginMember;
import finalmission.infra.thirdparty.dto.RestDayRequest;
import finalmission.repository.StoreRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StoreService {

    private final MemberService memberService;
    private final RestDataService restDataService;
    private final StoreRepository storeRepository;

    public StoreService(MemberService memberService, RestDataService restDataService, StoreRepository storeRepository) {
        this.memberService = memberService;
        this.restDataService = restDataService;
        this.storeRepository = storeRepository;
    }

    @Transactional
    public StoreCreateResponse createStore(StoreCreateRequest request, LoginMember loginMember) {
        MemberResponse memberResponse = memberService.findById(loginMember.id());
        Member owner = new Member(memberResponse.id(), memberResponse.email(), memberResponse.name(),
                memberResponse.memberRole());
        validateOwnerRole(owner);

        Store store = new Store(
                request.storeName(),
                StoreStatus.CLOSED,
                request.description(),
                0.0,
                owner
        );

        return StoreCreateResponse.from(storeRepository.save(store));
    }

    @Transactional
    public AddWaitingResponse addWaiting(AddWaitingRequest addWaitingRequest, LoginMember loginMember) {
        Store store = findStoreById(addWaitingRequest.storeId());
        validateStoreIsOpen(store);

        if (restDataService.checkRestDay(new RestDayRequest(LocalDate.now().getYear(), LocalDate.now().getMonthValue(),
                LocalDate.now().getDayOfMonth()))) {
            throw new IllegalArgumentException("[ERROR] 공휴일에 테이블링은 불가능합니다.");
        }
        MemberResponse memberResponse = memberService.findById(loginMember.id());
        Member member = new Member(memberResponse.id(), memberResponse.email(), memberResponse.name(),
                memberResponse.memberRole());
        validateCustomerRole(member);

        store.addWaiting(member);
        int rank = store.getWaitingRank(member);
        return new AddWaitingResponse(rank);
    }

    @Transactional
    public void removeWaiting(Long storeId, LoginMember loginMember) {
        Store store = findStoreById(storeId);
        MemberResponse memberResponse = memberService.findById(loginMember.id());
        Member member = new Member(memberResponse.id(), memberResponse.email(), memberResponse.name(),
                memberResponse.memberRole());
        store.removeWaitingMember(member);
    }

    @Transactional
    public void updateStoreStatus(Long storeId, String status, LoginMember loginMember) {
        Store store = findStoreById(storeId);
        StoreStatus storeStatus = StoreStatus.valueOf(status);
        MemberResponse memberResponse = memberService.findById(loginMember.id());
        Member owner = new Member(memberResponse.id(), memberResponse.email(), memberResponse.name(),
                memberResponse.memberRole());
        validateStoreOwner(store, owner);
        store.updateStatus(storeStatus);
    }

    public RankResponse getWaitingRank(Long storeId, LoginMember loginMember) {
        Store store = findStoreById(storeId);
        MemberResponse memberResponse = memberService.findById(loginMember.id());
        Member member = new Member(memberResponse.id(), memberResponse.email(), memberResponse.name(),
                memberResponse.memberRole());
        int rank = store.getWaitingRank(member);
        return new RankResponse(rank);
    }

    public List<StoreResponse> getAllStores() {
        return storeRepository.findAll().stream()
                .map(StoreResponse::from)
                .toList();
    }

    public List<StoreResponse> getAllOpenedStores() {
        return storeRepository.findAllByStoreStatus(StoreStatus.OPEN).stream()
                .map(StoreResponse::from)
                .toList();
    }

    private Store findStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 가게가 존재하지 않습니다."));
    }

    private void validateOwnerRole(Member member) {
        if (member.getMemberRole() != MemberRole.MASTER) {
            throw new IllegalStateException("가게 등록은 사장님만 가능합니다.");
        }
    }

    private void validateCustomerRole(Member member) {
        if (member.getMemberRole() != MemberRole.CUSTOMER) {
            throw new IllegalStateException("대기열 등록은 고객만 가능합니다.");
        }
    }

    private void validateStoreOwner(Store store, Member owner) {
        if (!store.getMember().equals(owner)) {
            throw new IllegalStateException("해당 가게의 사장님만 상태를 변경할 수 있습니다.");
        }
    }

    private void validateStoreIsOpen(Store store) {
        if (store.getStoreStatus() != StoreStatus.OPEN) {
            throw new IllegalStateException("영업 중인 가게만 대기열에 등록할 수 있습니다.");
        }
    }
}
