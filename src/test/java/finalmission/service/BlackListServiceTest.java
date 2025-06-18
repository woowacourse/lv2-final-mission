package finalmission.service;

import finalmission.domain.MemberRole;
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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlackListServiceTest {

    @Mock
    BlackListRepository blackListRepository;

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    BlackListService blackListService;

    Member member;

    @BeforeEach
    void setup() {
        member = new Member(1L, "test@test.com", "flint", "1234", MemberRole.MEMBER);
    }

    @Test
    @DisplayName("블랙리스트를 조회한다.")
    void findAllBlackList() {
        //given
        when(blackListRepository.findAllFetch())
                .thenReturn(List.of(new BlackList(1L, member, "테스트")));

        //when
        List<BlackListResponse> actual = blackListService.findAllBlackList();

        //then
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("특정 사용자를 블랙리스트에 추가한다.")
    void addBlackList() {
        //given
        CreateBlackListRequest request = new CreateBlackListRequest(1L, "테스트");

        when(memberRepository.findById(request.memberId()))
                .thenReturn(Optional.of(member));

        when(blackListRepository.existsByMemberId(any(Long.class)))
                .thenReturn(false);

        when(blackListRepository.save(any(BlackList.class)))
                .thenReturn(new BlackList(1L, member, "테스트"));

        //when
        CreateBlackListResponse actual = blackListService.addBlackList(request);

        //then
        CreateBlackListResponse expected = new CreateBlackListResponse(1L, 1L, member.getName(), "테스트", null);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("서비스에 가입하지 않은 유저는 블랙리스트에 추가할 수 없다.")
    void cannotAddNotSignUpMember() {
        //given
        CreateBlackListRequest request = new CreateBlackListRequest(1L, "테스트");

        when(memberRepository.findById(request.memberId()))
                .thenReturn(Optional.empty());

        //when //then
        assertThatThrownBy(() -> blackListService.addBlackList(request))
                .isInstanceOf(NotExistedValueException.class)
                .hasMessageContaining("존재하지 않는 유저입니다.");
    }

    @Test
    @DisplayName("이미 존재하는 유저는 블랙리스트에 추가할 수 없다.")
    void cannotAddExistMemberInBlackList() {
        //given
        CreateBlackListRequest request = new CreateBlackListRequest(1L, "테스트");

        when(memberRepository.findById(request.memberId()))
                .thenReturn(Optional.of(member));

        when(blackListRepository.existsByMemberId(any(Long.class)))
                .thenReturn(true);

        //when //then
        assertThatThrownBy(() -> blackListService.addBlackList(request))
                .isInstanceOf(DuplicatedValueException.class)
                .hasMessageContaining("이미 밴 처리된 유저입니다.");
    }

    @Test
    @DisplayName("블랙리스트에서 특정 유저를 제거한다.")
    void deleteBlackList() {
        //given
        when(blackListRepository.existsById(any(Long.class)))
                .thenReturn(true);

        //when //then
        assertDoesNotThrow(() -> blackListService.deleteBlackList(1L));
    }

    @Test
    @DisplayName("블랙리스트에 존재하지 않는 유저는 제거할 수 없다.")
    void cannotDeleteBlackList() {
        //given
        when(blackListRepository.existsById(any(Long.class)))
                .thenReturn(false);

        //when //then
        assertThatThrownBy(() -> blackListService.deleteBlackList(1L))
                .isInstanceOf(NotExistedValueException.class)
                .hasMessageContaining("존재하지 않는 밴 정보입니다.");
    }
}
