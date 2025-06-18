package finalmission.member.domain;

import finalmission.member.ui.dto.LoginRequest;

public interface MemberRepository {

    Member getById(Long id);

    Member signIn(LoginRequest request);
}
