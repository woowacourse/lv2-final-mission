package finalmission.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import finalmission.domain.Member;
import finalmission.domain.MemberRole;
import finalmission.domain.Store;
import finalmission.domain.StoreStatus;
import finalmission.domain.WaitingLine;
import finalmission.dto.request.AddWaitingRequest;
import finalmission.dto.request.StoreCreateRequest;
import finalmission.dto.response.AddWaitingResponse;
import finalmission.dto.response.MemberResponse;
import finalmission.dto.response.RankResponse;
import finalmission.dto.response.StoreCreateResponse;
import finalmission.dto.response.StoreResponse;
import finalmission.infra.auth.LoginMember;
import finalmission.repository.StoreRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @InjectMocks
    private StoreService storeService;

    @Mock
    private MemberService memberService;

    @Mock
    private RestDataService restDataService;

    @Mock
    private StoreRepository storeRepository;

    @Test
    void 가게를_추가한다() {
        // given
        StoreCreateRequest storeCreateRequest = new StoreCreateRequest("storeName", "description");

        LoginMember loginMember = new LoginMember(1L, "email", "name", MemberRole.MASTER);

        MemberResponse memberResponse = new MemberResponse(1L, "email", "name", "password", MemberRole.MASTER);
        Member owner = new Member(memberResponse.id(), memberResponse.email(), memberResponse.name(),
                memberResponse.memberRole());
        Store store = new Store("storeName", StoreStatus.CLOSED, "descrption", 0.0, owner);
        Store savedStore = new Store(1L, "storeName", StoreStatus.CLOSED, "descrption", 0.0, owner,
                WaitingLine.makeNewWaiting(store));

        // when
        when(memberService.findById(loginMember.id())).thenReturn(memberResponse);
        when(storeRepository.save(store)).thenReturn(savedStore);

        StoreCreateResponse storeCreateResponse = storeService.createStore(storeCreateRequest, loginMember);

        // then
        assertAll(() -> {
            assertThat(storeCreateResponse.id()).isEqualTo(1);
            assertThat(storeCreateResponse.storeName()).isEqualTo("storeName");
        });
    }

    @Test
    void 대기를_추가한다() {
        // given
        AddWaitingRequest addWaitingRequest = new AddWaitingRequest(1L);
        LoginMember loginMember = new LoginMember(1L, "email", "name", MemberRole.CUSTOMER);
        MemberResponse memberResponse = new MemberResponse(1L, "email", "name", "password", MemberRole.CUSTOMER);
        Member customer = new Member(memberResponse.id(), memberResponse.email(), memberResponse.name(),
                memberResponse.memberRole());
        Member owner = new Member(2L, "owner@email.com", "owner", MemberRole.MASTER);
        Store store = new Store("storeName", StoreStatus.OPEN, "description", 0.0, owner);
        Store savedStore = new Store(1L, "storeName", StoreStatus.OPEN, "description", 0.0, owner,
                WaitingLine.makeNewWaiting(store));

        when(storeRepository.findById(1L)).thenReturn(Optional.of(savedStore));
        when(restDataService.checkRestDay(any())).thenReturn(false);
        when(memberService.findById(1L)).thenReturn(memberResponse);

        // when
        AddWaitingResponse response = storeService.addWaiting(addWaitingRequest, loginMember);

        // then
        assertThat(savedStore.getWaitingLine().getSequenceByMember(customer)).isEqualTo(response.rank());
    }

    @Test
    void 가게_상태를_변경한다() {
        // given
        Long storeId = 1L;
        StoreStatus newStatus = StoreStatus.OPEN;
        LoginMember loginMember = new LoginMember(1L, "email", "name", MemberRole.MASTER);
        MemberResponse memberResponse = new MemberResponse(1L, "email", "name", "password", MemberRole.MASTER);
        Member owner = new Member(memberResponse.id(), memberResponse.email(), memberResponse.name(),
                memberResponse.memberRole());
        Store store = new Store("storeName", StoreStatus.CLOSED, "description", 0.0, owner);
        Store savedStore = new Store(storeId, "storeName", StoreStatus.CLOSED, "description", 0.0, owner,
                WaitingLine.makeNewWaiting(store));

        // when
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(savedStore));
        when(memberService.findById(loginMember.id())).thenReturn(memberResponse);

        storeService.updateStoreStatus(storeId, newStatus.name(), loginMember);

        // then
        assertThat(savedStore.getStoreStatus()).isEqualTo(newStatus);
    }

    @Test
    void 대기를_제거한다() {
        // given
        Long storeId = 1L;
        LoginMember loginMember = new LoginMember(1L, "email", "name", MemberRole.CUSTOMER);
        MemberResponse memberResponse = new MemberResponse(1L, "email", "name", "password", MemberRole.CUSTOMER);
        Member customer = new Member(memberResponse.id(), memberResponse.email(), memberResponse.name(),
                memberResponse.memberRole());
        Member owner = new Member(2L, "owner@email.com", "owner", MemberRole.MASTER);
        Store store = new Store("storeName", StoreStatus.OPEN, "description", 0.0, owner);
        Store savedStore = new Store(storeId, "storeName", StoreStatus.OPEN, "description", 0.0, owner,
                WaitingLine.makeNewWaiting(store));

        savedStore.addWaiting(customer);
        assertThat(savedStore.getWaitingLine().getSequenceByMember(customer)).isEqualTo(1);

        // when
        savedStore.removeWaitingMember(customer);

        // then
        assertThatThrownBy(() -> savedStore.getWaitingLine().getSequenceByMember(customer))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 대기_순서를_조회한다() {
        // given
        Long storeId = 1L;
        LoginMember loginMember = new LoginMember(1L, "email", "name", MemberRole.CUSTOMER);
        MemberResponse memberResponse = new MemberResponse(1L, "email", "name", "password", MemberRole.CUSTOMER);
        Member customer = new Member(memberResponse.id(), memberResponse.email(), memberResponse.name(),
                memberResponse.memberRole());
        Member owner = new Member(2L, "owner@email.com", "owner", MemberRole.MASTER);
        Store store = new Store("storeName", StoreStatus.OPEN, "description", 0.0, owner);
        Store savedStore = new Store(storeId, "storeName", StoreStatus.OPEN, "description", 0.0, owner,
                WaitingLine.makeNewWaiting(store));
        savedStore.addWaiting(customer);

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(savedStore));
        when(memberService.findById(loginMember.id())).thenReturn(memberResponse);

        // when
        RankResponse response = storeService.getWaitingRank(storeId, loginMember);

        // then
        assertThat(response.rank()).isEqualTo(1);
    }

    @Test
    void 모든_가게를_조회한다() {
        // given
        Member owner = new Member(1L, "owner@email.com", "owner", MemberRole.MASTER);
        Store store1 = new Store("store1", StoreStatus.OPEN, "description1", 0.0, owner);
        Store savedStore1 = new Store(1L, "store1", StoreStatus.OPEN, "description1", 0.0, owner,
                WaitingLine.makeNewWaiting(store1));
        Store store2 = new Store("store2", StoreStatus.CLOSED, "description2", 0.0, owner);
        Store savedStore2 = new Store(2L, "store2", StoreStatus.CLOSED, "description2", 0.0, owner,
                WaitingLine.makeNewWaiting(store2));

        // when
        when(storeRepository.findAll()).thenReturn(List.of(savedStore1, savedStore2));

        List<StoreResponse> response = storeService.getAllStores();

        // then
        assertAll(() -> {
            assertThat(response).hasSize(2);
            assertThat(response.get(0).storeName()).isEqualTo("store1");
            assertThat(response.get(1).storeName()).isEqualTo("store2");
        });
    }

    @Test
    void 영업중인_가게를_조회한다() {
        // given
        Member owner = new Member(1L, "owner@email.com", "owner", MemberRole.MASTER);
        Store store1 = new Store("store1", StoreStatus.OPEN, "description1", 0.0, owner);
        Store savedStore1 = new Store(1L, "store1", StoreStatus.OPEN, "description1", 0.0, owner,
                WaitingLine.makeNewWaiting(store1));
        Store store2 = new Store("store2", StoreStatus.CLOSED, "description2", 0.0, owner);
        Store savedStore2 = new Store(2L, "store2", StoreStatus.CLOSED, "description2", 0.0, owner,
                WaitingLine.makeNewWaiting(store2));

        // when
        when(storeRepository.findAllByStoreStatus(StoreStatus.OPEN)).thenReturn(List.of(savedStore1));

        List<StoreResponse> response = storeService.getAllOpenedStores();

        // then
        assertAll(() -> {
            assertThat(response).hasSize(1);
            assertThat(response.getFirst().storeName()).isEqualTo("store1");
            assertThat(response.getFirst().storeStatus()).isEqualTo(StoreStatus.OPEN);
        });
    }
}
